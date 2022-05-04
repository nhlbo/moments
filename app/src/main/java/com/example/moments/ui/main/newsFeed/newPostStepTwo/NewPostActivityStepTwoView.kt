package com.example.moments.ui.main.newsFeed.newPostStepTwo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.MainActivityView
import com.google.android.material.appbar.MaterialToolbar
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

class NewPostActivityStepTwoView : BaseActivity(), INewPostStepTwoView {

    private var imageData: ArrayList<ByteArray> = arrayListOf()
    private var videoUri: String = ""
    private lateinit var toolBar: MaterialToolbar
    private lateinit var caption: EditText
    private var uploadType: Int = 0
    @Inject
    lateinit var presenter: INewPostStepTwoPresenter<INewPostStepTwoView, INewPostStepTwoInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post_step2)
        presenter.onAttach(this)

        analyzeTypeUpload()
        caption = findViewById(R.id.et_NewPost2)
        initToolBar()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    private fun analyzeTypeUpload(){
        uploadType = intent.extras?.getInt("uploadType")!!

        if(uploadType == 0){ // up images{
            val size = intent.extras?.getInt("size")!!
            for(i:Int in 0 until size){
                intent.extras?.getByteArray("imageData $i")?.let { imageData.add(it) }
            }
        }
        else if(uploadType == 1){
            videoUri = intent.extras?.getString("videoLink")!!
        }
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    private fun initToolBar() {
        toolBar = findViewById(R.id.tb_newPostStep2)
        toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.confirmNewPost -> {
                    if(uploadType == 0) presenter.onCreatePost(caption.text.toString(), imageData)
                    else if(uploadType == 1) presenter.onCreateMoment(caption.text.toString(), convertStringToByteArray())
                    true
                }
                else -> false
            }
        }
        toolBar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun backToFeedActivity() {
        val intent = Intent(this, MainActivityView::class.java)
        startActivity(intent)
    }

    private fun convertStringToByteArray():ByteArray{
        val baos = ByteArrayOutputStream()
        val fis = FileInputStream(File(videoUri))

        val buf = ByteArray(1024)
        var n: Int
        while (-1 != fis.read(buf).also { n = it }) baos.write(buf, 0, n)

        return baos.toByteArray()
    }
}