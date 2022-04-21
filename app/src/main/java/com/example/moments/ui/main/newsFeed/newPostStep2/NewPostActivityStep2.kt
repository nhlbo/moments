package com.example.moments.ui.main.newsFeed.newPostStep2

import android.os.Bundle
import android.widget.EditText
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.google.android.material.appbar.MaterialToolbar

class NewPostActivityStep2 : BaseActivity() {

    private var imageData: ArrayList<ByteArray>? = null
    private lateinit var toolBar: MaterialToolbar
    private lateinit var caption: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post_step2)
        imageData = parseToListByteArray(intent.getStringArrayExtra("itemData"))

        caption = findViewById(R.id.et_NewPost2)

        initToolBar()

    }

    private fun parseToListByteArray(data: Array<String>?) : ArrayList<ByteArray>?{
        if(data == null) return null
        val result: ArrayList<ByteArray> = arrayListOf()
        for(str: String in data){
            result.add(str.toByteArray())
        }
        return result
    }

    override fun onFragmentAttached() {
        TODO("Not yet implemented")
    }

    override fun onFragmentDetached(tag: String) {
        TODO("Not yet implemented")
    }

    private fun initToolBar(){
        toolBar = findViewById(R.id.tb_newPostStep2)
        toolBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.confirmNewPost -> {
                    //TODO post
                    true
                }
                else -> false
            }
        }
        toolBar.setNavigationOnClickListener {
            finish()
        }
    }

}