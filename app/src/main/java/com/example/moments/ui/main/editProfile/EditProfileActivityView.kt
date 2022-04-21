package com.example.moments.ui.main.editProfile

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseActivity
import com.example.moments.ui.main.viewProfile.ProfileFragmentView
import kotlinx.android.synthetic.main.activity_edit_profile.*
import javax.inject.Inject

class EditProfileActivityView : BaseActivity(), IEditProfileActivityView {

    @Inject
    lateinit var presenter: IEditProfileActivityPresenter<IEditProfileActivityView, IEditProfileActivityInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        presenter.onAttach(this)
        tbEditProfileActivity.setNavigationOnClickListener { finish() }

        val user = intent.getParcelableExtra<User>(ProfileFragmentView.USER_KEY)!!
        etUsernameEditProfile.setText(user.username)
        etBioEditProfile.setText(user.username)
        Glide.with(this).load(user.avatar).into(ivAvatarEditProfile)
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}
