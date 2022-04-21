package com.example.moments.ui.main.newsFeed.newPostStepTwo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.MainActivityView
import com.google.android.material.appbar.MaterialToolbar
import javax.inject.Inject

class NewPostActivityStepTwoView : BaseActivity(), INewPostStepTwoView {

    private lateinit var imageData: List<ByteArray>
    private lateinit var toolBar: MaterialToolbar
    private lateinit var caption: EditText

    @Inject
    lateinit var presenter: INewPostStepTwoPresenter<INewPostStepTwoView, INewPostStepTwoInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post_step2)
        presenter.onAttach(this)
        Log.d("debug", intent.getStringArrayExtra("itemData").toString())
        imageData = parseToListByteArray(intent.getStringArrayExtra("itemData"))

        caption = findViewById(R.id.et_NewPost2)
        Log.d("debug", imageData.toString())

        initToolBar()

    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    private fun parseToListByteArray(data: Array<String>?): List<ByteArray>? {
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
                    presenter.onCreatePost(caption.text.toString(), imageData)
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