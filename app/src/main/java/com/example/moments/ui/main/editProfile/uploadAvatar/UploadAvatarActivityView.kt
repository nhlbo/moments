package com.example.moments.ui.main.editProfile.uploadAvatar

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.ui.customClasses.IOnRecyclerViewItemTouchListener
import com.example.moments.ui.main.newsFeed.newPost.ImageChoosingAdapter
import com.example.moments.ui.main.viewProfile.ImagesAdapter
import kotlinx.android.synthetic.main.activity_upload_avatar.*
import java.io.ByteArrayOutputStream
import kotlin.math.min

class UploadAvatarActivityView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_avatar)
        requestPermission()

        val viewContainer = layoutInflater.inflate(R.layout.component_grid_media, MediaUploadAvaContainer, true)
        initRecyclerView(viewContainer)
        onScrollListener()
        onToolBarButtonsClicked()
    }

    private var recyclerView: RecyclerView? = null
    private lateinit var imageList: ArrayList<String>
    private var currentChosePosition = -1

    private val listener: IOnRecyclerViewItemTouchListener =
        object : IOnRecyclerViewItemTouchListener {
            override fun onItemClick(position: Int) {
                Glide.with(this@UploadAvatarActivityView).load(imageList[position]).into(iv_avatarUploadPreview!!)
                val itemHeight = recyclerView?.y!! + recyclerView?.getChildAt(position)?.y!!

                nestedScrollViewUploadAva?.post {
                    nestedScrollViewUploadAva?.fling(0)
                    nestedScrollViewUploadAva?.scrollTo(0, itemHeight.toInt())
                }
                appbarUploadAva?.setExpanded(true)
                currentChosePosition = position
            }
        }

    private val PICK_FROM_GALLERY = 1
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

    @SuppressLint("Recycle")
    private fun getAllShownImagesPath(activity: Activity): ArrayList<String> {
        val cursor: Cursor?
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String?

        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
        cursor = activity.contentResolver.query(
            uri, projection, null,
            null, MediaStore.Images.ImageColumns.DATE_TAKEN
        )
        val columnIndexData: Int? = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor?.moveToNext() == true) {
            absolutePathOfImage = columnIndexData?.let { cursor.getString(it) }
            if (absolutePathOfImage != null) {
                listOfAllImages.add(absolutePathOfImage)
            }
        }
        return ArrayList(listOfAllImages.reversed())
    }

    private fun initRecyclerView(view: View?){
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
        val adapter = ImagesAdapter(this, ArrayList(imageList.subList(0, min(imageList.size, 20))), listener)
        recyclerView?.adapter = adapter
    }

    private var loading = true
    private fun onScrollListener(){
        nestedScrollViewUploadAva?.setOnScrollChangeListener { v: NestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
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
        (recyclerView?.adapter as ImagesAdapter).updateItems(
            imageList.subList(
                total,
                total + fetch
            )
        )
    }

    private fun onToolBarButtonsClicked(){
        tbUploadAvatar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.upload_avatar_confirm -> {
                    onReturnData()
                    true
                }
                else->false
            }
        }

        tbUploadAvatar.setNavigationOnClickListener {
            setResult(RESULT_CANCELED, Intent())
            finish()
        }
    }

    private fun convertImageToByteArray(view: ImageView): ByteArray {
        val drawable = (view.drawable as BitmapDrawable)
        val bitmap = drawable.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        return stream.toByteArray()
    }

    private fun onReturnData(){
        val data = convertImageToByteArray(iv_avatarUploadPreview)

        val intent = Intent()
        intent.putExtra("ava", data)
        setResult(RESULT_OK, intent)
        finish()
    }

}