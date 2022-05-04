package com.example.moments.ui.vuforia

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.res.AssetManager
import android.opengl.GLSurfaceView
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NavUtils
import androidx.core.view.GestureDetectorCompat
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.viewOtherProfile.OtherProfileActivityView
import kotlinx.android.synthetic.main.activity_vuforia.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer
import java.util.*
import javax.inject.Inject
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.concurrent.schedule


/**
 * Activity to demonstrate how to use Vuforia Image Target and Model Target features,
 * Video Background rendering and Vuforia lifecycle.
 */
class VuforiaActivityView : BaseActivity(), IVuforiaActivityView, GLSurfaceView.Renderer,
    SurfaceHolder.Callback {
    @Inject
    lateinit var presenter: IVuforiaActivityPresenter<IVuforiaActivityView, IVuforiaActivityInteractor>

    private lateinit var mGLView: GLSurfaceView

    private var mTarget = 0
    private var mProgressIndicatorLayout: RelativeLayout? = null

    private var mWidth = 0
    private var mHeight = 0

    private var mVuforiaStarted = false
    private var mSurfaceChanged = false

    private var mGestureDetector: GestureDetectorCompat? = null
    private lateinit var mName: TextView
    private lateinit var mPanel: LinearLayout
    private lateinit var mBack: ImageButton
    private lateinit var mImage: ImageView
    private lateinit var mMsg: TextView

    // Native methods
    private external fun initAR(activity: Activity, assetManager: AssetManager, target: Int)
    private external fun deinitAR()

    private external fun startAR(): Boolean
    private external fun stopAR()

    private external fun pauseAR()
    private external fun resumeAR()

    private external fun cameraPerformAutoFocus()
    private external fun cameraRestoreAutoFocus()

    private external fun initRendering()
    private external fun setTextures(
        astronautWidth: Int, astronautHeight: Int, astronautBytes: ByteBuffer,
        landerWidth: Int, landerHeight: Int, landerBytes: ByteBuffer
    )

    private external fun deinitRendering()
    private external fun configureRendering(width: Int, height: Int, orientation: Int): Boolean
    private external fun renderFrame(): String
    private var vuforiaId: String = ""

    // Activity methods
    open fun setTimeoutSync(runnable: java.lang.Runnable, delay: Int) {
        try {
            Thread.sleep(delay.toLong())
            runnable.run()
        } catch (e: java.lang.Exception) {
            System.err.println(e)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_vuforia)

        presenter.onAttach(this)

        System.loadLibrary("VuforiaSample")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val windowParams = window.attributes
            windowParams.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = windowParams
        }

        mVuforiaStarted = false
        mSurfaceChanged = true
        // Create an OpenGL ES 3.0 context (also works for 3.1, 3.2)
        mGLView = findViewById(R.id.cameraview)
        mBack = findViewById(R.id.back)

        mGLView.holder.addCallback(this)
        mGLView.setEGLContextClientVersion(3)
        mGLView.setRenderer(this)
        cameraPerformAutoFocus()
        // Hide the GLView until we are ready
        mBack.setOnClickListener {
            onBackPressed()
        }
        mName = findViewById(R.id.name)
        mImage = findViewById(R.id.image)
        mMsg = findViewById(R.id.msg)
        mPanel = findViewById(R.id.panel)
        mPanel.setOnClickListener {

        }
        // Prevent screen from dimming
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Make the Activity completely full screen
        mGLView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup and show a progress indicator
        mProgressIndicatorLayout = View.inflate(
            applicationContext,
            R.layout.progress_indicator, null
        ) as RelativeLayout

        addContentView(
            mProgressIndicatorLayout, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        // Start Vuforia initialization in a coroutine
        GlobalScope.launch(Dispatchers.Unconfined) {
            initializeVuforia()
        }

        mGestureDetector = GestureDetectorCompat(this, GestureListener())
    }


    override fun onPause() {
        pauseAR()
        super.onPause()
    }


    override fun onResume() {
        super.onResume()

        if (mVuforiaStarted) {
            GlobalScope.launch(Dispatchers.Unconfined) {
                resumeAR()
            }
        }
    }


    override fun onBackPressed() {
        stopAR()
        mVuforiaStarted = false
        deinitAR()
        super.onBackPressed()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    // Overrider onTouchEvent to connect it to our GestureListener
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mGestureDetector?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }


    /// Custom GestureListener to capture single and double tap
    inner class GestureListener : SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            // Calls the Autofocus Native Method
            cameraPerformAutoFocus()
            GlobalScope.launch(Dispatchers.Main) {
                mPanel.visibility = View.INVISIBLE
                vuforiaId = "";
                mMsg.visibility = View.VISIBLE
            }
            // After triggering a focus event wait 2 seconds
            // before restoring continuous autofocus
            Timer("RestoreAutoFocus", false).schedule(2000) {
                cameraRestoreAutoFocus()
            }

            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            return true
        }
    }


    private suspend fun initializeVuforia() {
        return withContext(Dispatchers.Default) {
            initAR(this@VuforiaActivityView, this@VuforiaActivityView.assets, mTarget)
        }
    }


    private fun presentError(message: String) {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage(message)
        builder?.setTitle(R.string.error_dialog_title)
        builder?.setPositiveButton(
            R.string.ok,
            DialogInterface.OnClickListener { _, _ ->
                stopAR()
                deinitAR()
                this@VuforiaActivityView.finish()
            })

        // This is called from another coroutine not on the Main thread
        // Showing the UI needs to be on the main thread
        GlobalScope.launch(Dispatchers.Main) {
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
        }
    }


    private fun initDone() {
        mVuforiaStarted = startAR()
        if (!mVuforiaStarted) {
            Log.e("VuforiaSample", "Failed to start AR")
        }
        // Show the GLView
        GlobalScope.launch(Dispatchers.Main) {
            mGLView.visibility = View.VISIBLE
        }
    }


    // GLSurfaceView.Renderer methods
    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        initRendering()
    }


    override fun onDrawFrame(unused: GL10) {
        if (mVuforiaStarted) {

            if (mSurfaceChanged) {
                mSurfaceChanged = false

                // This sample doesn't support auto-rotation, landscape orientation is hard coded here
                configureRendering(mWidth, mHeight, /* Landscape Left */1)
            }
            GlobalScope.launch(Dispatchers.Main) {
                if (mProgressIndicatorLayout?.visibility != View.GONE)
                    mProgressIndicatorLayout?.visibility = View.GONE

            }
            // OpenGL rendering of Video Background and augmentations is implemented in native code
            var didRender = renderFrame()
            if (didRender.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.Main) {
                    if (vuforiaId != didRender) {
                        vuforiaId = didRender
                       renderInformation(vuforiaId)

                    }
                }
            }

        }
    }


    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        // Store values for later use
        mWidth = width
        mHeight = height

        // Re-load textures in case they got destroyed


        // Update flag to tell us we need to update Vuforia configuration
        mSurfaceChanged = true
    }

    // SurfaceHolder.Callback
    override fun surfaceCreated(p0: SurfaceHolder) {}


    override fun surfaceChanged(p0: SurfaceHolder, var2: Int, var3: Int, var4: Int) {}


    override fun surfaceDestroyed(p0: SurfaceHolder) {
        deinitRendering()
    }

    private fun renderInformation(vuforiaId: String) {
        presenter.onPerformQueryUserByVuforiaId(vuforiaId)
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    override fun getUserByVuforiaId(user: User) {
        if (user.id == "") {
            mMsg.visibility = View.VISIBLE
            mPanel.visibility = View.INVISIBLE
            return
        }
        Glide.with(this).load(user.avatar).into(mImage)
        mName.text = user.username
        tvBioVuforia.text = user.bio
        btnViewProfileVuforia.setOnClickListener {
            val intent = Intent(this, OtherProfileActivityView::class.java)
            intent.putExtra("USER_ID", user.id)
            startActivity(intent)
        }
        mMsg.visibility = View.INVISIBLE
        if (mPanel.visibility != View.VISIBLE)
            mPanel.visibility = View.VISIBLE
    }
}
