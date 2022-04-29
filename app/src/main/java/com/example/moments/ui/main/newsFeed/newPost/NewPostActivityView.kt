package com.example.moments.ui.main.newsFeed.newPost

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.customClasses.IOnRecyclerViewItemTouchListener
import com.example.moments.ui.main.newsFeed.newPostStepTwo.NewPostActivityStepTwoView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_search.*
import java.io.ByteArrayOutputStream
import kotlin.math.min


class NewPostActivityView : BaseActivity(), INewPostActivityView {
    private val PICK_FROM_GALLERY = 1
    private var previewImage: ImageView? = null
    private var nestedScrollView: NestedScrollView? = null
    private var appBar: AppBarLayout? = null
    private lateinit var toolBar: MaterialToolbar
    private var loading: Boolean = true
    private lateinit var imageList: ArrayList<String>
    private var recyclerView: RecyclerView? = null
    private var toggleSelection: ToggleButton? = null

    private val listener: IOnRecyclerViewItemTouchListener =
        object : IOnRecyclerViewItemTouchListener {
            override fun onItemClick(position: Int) {
                Glide.with(this@NewPostActivityView).load(imageList[position]).into(previewImage!!)
                val itemHeight = recyclerView?.y!! + recyclerView?.getChildAt(position)?.y!!

                nestedScrollView?.post {
                    nestedScrollView?.fling(0)
                    nestedScrollView?.scrollTo(0, itemHeight.toInt())
                }
                appBar?.setExpanded(true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post_view)
        requestPermission()

        val frameLayout = findViewById<FrameLayout>(R.id.MediaNewPostContainer) as ViewGroup
        val mediaGrid = layoutInflater.inflate(R.layout.component_grid_media, frameLayout, true)
        previewImage = findViewById(R.id.iv_newPostPreview)
        nestedScrollView = findViewById(R.id.nestedScrollViewNewPost)
        appBar = findViewById(R.id.appbarNewPost)
        toolBar = findViewById(R.id.tbNewPost)
        toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.new_post_post -> {
                    val intent = Intent(this, NewPostActivityStepTwoView::class.java)
                    val listData = getSelectedImageByteArray()
                    intent.putExtra("size", listData.size)
                    for(i :Int in 0 until listData.size){
                        intent.putExtra("imageData $i", listData[i])
                    }
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        toolBar.setNavigationOnClickListener { finish() }

        toggleSelection = findViewById(R.id.toggleBtnSelectionNewPost)
        toggleSelection?.setOnClickListener {
            (recyclerView?.adapter as ImageChoosingAdapter).setChosingType(toggleSelection?.isChecked!!)
        }

        initRecyclerView(mediaGrid)
    }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PICK_FROM_GALLERY
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PICK_FROM_GALLERY ->                 // If request is cancelled, the result arrays are empty.
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    finish()
                }
        }
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Recycle")
    private fun getAllShownImagesPath(activity: Activity): ArrayList<String> {
        val cursor: Cursor?
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String?

        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
        cursor = activity.contentResolver.query(
            uri, projection, null,
            null, MediaStore.Images.ImageColumns.DATE_TAKEN
        )
        val columnIndexData: Int? = cursor?.getColumnIndexOrThrow(MediaColumns.DATA)
        while (cursor?.moveToNext() == true) {
            absolutePathOfImage = columnIndexData?.let { cursor.getString(it) }
            if (absolutePathOfImage != null) {
                listOfAllImages.add(absolutePathOfImage)
            }
        }
        return ArrayList(listOfAllImages.reversed())
    }

    private fun initRecyclerView(view: View?) {
        imageList = getAllShownImagesPath(this)

        recyclerView = view?.findViewById(R.id.rcMediaGrid)
        recyclerView?.setHasFixedSize(true);
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.HORIZONTAL
            )
        )
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView?.layoutManager = GridLayoutManager(this, 4)
        val adapter = ImageChoosingAdapter(this, ArrayList(imageList.subList(0, 20)), listener)
        recyclerView?.adapter = adapter
        recyclerView?.isNestedScrollingEnabled = false

        nestedScrollView?.setOnScrollChangeListener { v: NestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
                    scrollY > oldScrollY
                ) {
                    //code to fetch more data for endless scrolling
                    val mLayoutManager = recyclerView?.layoutManager as GridLayoutManager
                    val totalItemCount = mLayoutManager.itemCount
                    if (loading) {
                        loading = false
                        // Do pagination.. i.e. fetch new data
                        loadMore(totalItemCount)
                        loading = true
                    }
                }
            }
        }
    }

    private fun loadMore(total: Int) {
        val fetch = min(10, imageList.size - total)
        (recyclerView?.adapter as ImageChoosingAdapter).updateItems(
            imageList.subList(
                total,
                total + fetch
            )
        )
    }

    private fun getSelectedImageByteArray(): ArrayList<ByteArray> {
        val result: ArrayList<ByteArray> = arrayListOf()
        val selectedItems = (recyclerView?.adapter as ImageChoosingAdapter).selectedItems

        for (item: ImageView in selectedItems) {
            result.add(convertImageToByteArray(item))
        }
        return result
    }

    private fun convertImageToByteArray(view: ImageView): ByteArray {
        val drawable = (view.drawable as BitmapDrawable)
        val bitmap = drawable.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        return stream.toByteArray()
    }
}