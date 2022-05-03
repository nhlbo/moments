package com.example.moments.ui.main.editProfile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.test.core.app.ActivityScenario.launch
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.editProfile.uploadAvatar.UploadAvatarActivityView
import com.example.moments.ui.main.newsFeed.newPostStepTwo.NewPostActivityStepTwoView
import com.example.moments.ui.main.viewProfile.ProfileFragmentView
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class EditProfileActivityView : BaseActivity(), IEditProfileActivityView {
    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    @Inject
    lateinit var presenter: IEditProfileActivityPresenter<IEditProfileActivityView, IEditProfileActivityInteractor>

    companion object {
        const val DONE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        presenter.onAttach(this)
        tbEditProfileActivity.setNavigationOnClickListener { finish() }

        tbEditProfileActivity.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.btnDoneEditProfile) {
                presenter.onPerformEditProfile(
                    etUsernameEditProfile.text.toString(),
                    etBioEditProfile.text.toString()
                )
                val replyIntent = Intent()
                replyIntent.putExtra("USERNAME", etUsernameEditProfile.text.toString())
                replyIntent.putExtra("BIO", etBioEditProfile.text.toString())
                setResult(DONE, replyIntent)
                finish()
                return@setOnMenuItemClickListener true
            }
            return@setOnMenuItemClickListener false
        }

        val user = intent.getParcelableExtra<User>(ProfileFragmentView.USER_KEY)!!
        etUsernameEditProfile.setText(user.username)
        etBioEditProfile.setText(user.username)
        Glide.with(this).load(user.avatar).into(ivAvatarEditProfile)
        onUploadBtnClicked()
    }

    private fun onUploadBtnClicked() {
        btnChangeAvatarEditProfile.setOnClickListener {
            val intent = Intent(this, UploadAvatarActivityView::class.java)
            resultLauncher.launch(intent)
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val intent: Intent? = result.data
            val byteArray = intent?.extras!!["ava"] as ByteArray
            runBlocking {
                val job = launch(Dispatchers.Default) {
                    presenter.uploadAvatar(byteArray)
                }
                job.start()
            }
            val bitmap = convertByteArrayToBitmap(byteArray)
            Glide.with(this).load(bitmap).into(ivAvatarEditProfile)
        }
    }

    private fun convertByteArrayToBitmap(bitmapData : ByteArray) : Bitmap =
        BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.size);
}
