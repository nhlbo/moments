package com.example.moments.ui.login


import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity


class LoginActivityView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val a = findViewById<EditText>(R.id.editTextTextPersonName)
    }
}