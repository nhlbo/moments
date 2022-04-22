package com.example.moments.ui.main.newsFeed.newPostStepTwo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.MainActivityView
import com.google.android.material.appbar.MaterialToolbar
import javax.inject.Inject

class NewPostActivityStepTwoView : BaseActivity(), INewPostStepTwoView {

    private var imageData: List<ByteArray>? = null
    private lateinit var toolBar: MaterialToolbar
    private lateinit var caption: EditText

    @Inject
    lateinit var presenter: INewPostStepTwoPresenter<INewPostStepTwoView, INewPostStepTwoInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post_step2)
        presenter.onAttach(this)
        imageData = parseToListByteArray(intent.extras?.getStringArrayList("imageData"))

        caption = findViewById(R.id.et_NewPost2)

        initToolBar()

    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    private fun parseToListByteArray(data: java.util.ArrayList<String>?): List<ByteArray>? {
        if (data == null) return null
        val result: ArrayList<ByteArray> = arrayListOf()
        for (str: String in data) {
            result.add(str.toByteArray())
        }
        return result.toList()
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
                    if(imageData != null) presenter.onCreatePost(caption.text.toString(), imageData!!)
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

}