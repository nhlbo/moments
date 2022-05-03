package com.example.moments.ui.main.comment
import com.example.moments.data.model.RetrieviedComment
import com.example.moments.util.AppConstants

open class CommentData(
    val userId: String,
    val username: String,
    val avatar: String,
    val commentId: String,
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

    companion object{
        fun parseRetrieveComment(input:RetrieviedComment): CommentData =
            CommentData(
                userId = input.creator.id,
                username = input.creator.username,
                avatar = input.creator.avatar,
                commentId = input.id,
                content = input.content,
                reactions = input.likeCount,
                timeUpload = input.createdAt.toString()
            )
    }
}