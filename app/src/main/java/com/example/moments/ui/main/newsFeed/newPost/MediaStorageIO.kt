package com.example.moments.ui.main.newsFeed.newPost

import android.annotation.SuppressLint
import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

class MediaStorageIO {

    @SuppressLint("Recycle")
    fun getAllShownImagesPath(activity: Activity): ArrayList<String> {
        val listMediaPath = arrayListOf<String>()
        val cursor: Cursor?
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
                listMediaPath.add(absolutePathOfImage)
            }
        }
        return ArrayList(listMediaPath.reversed())
    }


}