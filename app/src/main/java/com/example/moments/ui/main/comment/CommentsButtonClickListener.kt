package com.example.moments.ui.main.comment

interface CommentsButtonClickListener{
    fun onReplyClicked(username:String, position:Int)
    fun onUserNameClicked(username:String, position: Int)
}