package com.example.moments.ui.main.savedPost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.R
import com.example.moments.ui.main.viewProfile.ImagesAdapter
import kotlinx.android.synthetic.main.activity_saved_post.*

class SavedPostActivityView : AppCompatActivity() {
    private var imageList = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_post)
        initRecyclerView()
        tbSavedPostActivity.setNavigationOnClickListener { finish() }
    }

    private fun initRecyclerView() {
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic3.png?alt=media&token=272b593d-eb42-4251-b88f-10c4aae740b3")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic3.png?alt=media&token=272b593d-eb42-4251-b88f-10c4aae740b3")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic3.png?alt=media&token=272b593d-eb42-4251-b88f-10c4aae740b3")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")

        val recyclerView = findViewById<RecyclerView>(R.id.rcSavedPostActivity)

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
        recyclerView?.layoutManager = GridLayoutManager(this, 3)
        val adapter = this?.let { ImagesAdapter(it, imageList, null) }
        recyclerView?.adapter = adapter
    }
}