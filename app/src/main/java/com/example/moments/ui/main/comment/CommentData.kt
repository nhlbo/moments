package com.example.moments.ui.main.comment
import com.example.moments.util.AppConstants

open class CommentData(
    val userId: Int,
    val username: String,
    val commentId: Int,
    val content: String,
    var reactions: Int,
    val timeUpload: String
){
    val tagPeople: ArrayList<String> = analyzeContent() // key: username, value: id
    private fun analyzeContent() : ArrayList<String>{
        val res = arrayListOf<String>()
        var tagPosition = content.indexOf('@', 0)

        while(tagPosition != -1 && tagPosition < content.length){
            val previousPosition = tagPosition++

            while(tagPosition < content.length && isValidChar(content[tagPosition])) tagPosition++
            if(tagPosition == content.length) break

            val username = content.subSequence(previousPosition,  tagPosition)
            res.add(username as String)
        }
        return res
    }

    private fun isValidChar(ch: Char):Boolean{
        return ch in 'a'..'z' || ch in AppConstants.validChar
    }
}