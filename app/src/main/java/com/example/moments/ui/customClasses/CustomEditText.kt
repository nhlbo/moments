package com.example.moments.ui.customClasses

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.moments.R

class CustomEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    private val STATE_ERROR = IntArray(1) { R.attr.state_error }
    var isError = false

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isError) {
            mergeDrawableStates(drawableState, STATE_ERROR)
        }
        return drawableState
    }
}