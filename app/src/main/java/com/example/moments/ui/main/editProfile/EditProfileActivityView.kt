package com.example.moments.ui.main.editProfile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.editProfile.uploadAvatar.UploadAvatarActivityView
import com.example.moments.ui.main.viewProfile.ProfileFragmentView
import com.example.moments.ui.vuforia.VuforiaAPI.PostNewTarget
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.Instant
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EditProfileActivityView : BaseActivity(), IEditProfileActivityView {
    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    @Inject
    lateinit var presenter: IEditProfileActivityPresenter<IEditProfileActivityView, IEditProfileActivityInteractor>

    companion object {
        const val DONE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        presenter.onAttach(this)
        tbEditProfileActivity.setNavigationOnClickListener { finish() }

        tbEditProfileActivity.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.btnDoneEditProfile) {
                runBlocking { // upload changes to db
                    val job = launch(Dispatchers.Default) {
                        if(byteArray != null){
                            presenter.uploadAvatar(byteArray!!)
                        }
                        presenter.onPerformEditProfile(
                            etUsernameEditProfile.text.toString(),
                            etBioEditProfile.text.toString()
                        )
                    }
                    job.start()
                }
                val replyIntent = Intent()
                replyIntent.putExtra("USERNAME", etUsernameEditProfile.text.toString())
                replyIntent.putExtra("BIO", etBioEditProfile.text.toString())
                setResult(DONE, replyIntent)
                finish()
                return@setOnMenuItemClickListener true
            }
            return@setOnMenuItemClickListener false
        }

        val user = intent.getParcelableExtra<User>(ProfileFragmentView.USER_KEY)!!
        etUsernameEditProfile.setText(user.username)
        etBioEditProfile.setText(user.bio)
        Glide.with(this).load(user.avatar).into(ivAvatarEditProfile)
        onUploadBtnClicked()
    }

    private fun onUploadBtnClicked() {
        btnChangeAvatarEditProfile.setOnClickListener {
            val intent = Intent(this, UploadAvatarActivityView::class.java)
            resultLauncher.launch(intent)
        }
    }

    private var byteArray: ByteArray? = null
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // get bytearray from image and show on imageview
                val intent: Intent? = result.data
                val path = intent?.extras!!["path"] as String
                byteArray = intent.extras!!["ava"] as ByteArray
                val thread = Thread {
                    try {
                        //Your code goes here
                        val p = PostNewTarget(
                            DateTimeFormatter.ISO_INSTANT.format(Instant.now()) + ".jpg",
                            path
                        )
                        p.postTargetThenPollStatus()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                thread.start()

                val bitmap = convertByteArrayToBitmap(byteArray!!)
                Glide.with(this).load(bitmap).into(ivAvatarEditProfile)
            }
        }

    private fun convertByteArrayToBitmap(bitmapData: ByteArray): Bitmap =
        BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.size);
}
