package com.example.moments.ui.login


import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import com.example.moments.R
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.custom_classes.CustomEditText
import com.google.android.material.textfield.TextInputLayout


class LoginActivityView : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}