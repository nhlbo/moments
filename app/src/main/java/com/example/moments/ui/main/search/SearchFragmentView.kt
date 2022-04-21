package com.example.moments.ui.main.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject


class SearchFragmentView : BaseFragment(), ISearchView {

    @Inject
    lateinit var presenter: ISearchPresenter<ISearchView, ISearchInteractor>

    companion object {
        fun newInstance(): SearchFragmentView {
            return SearchFragmentView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.activity_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun onSearchResultCallback(listUser: List<User>) {
        (svSearchOthers.adapter as UserSearchAdapter).updateUser(listUser)
        svSearchOthers.showDropDown()
    }

    override fun setUp() {
        val userSearchAdapter =
            UserSearchAdapter(context, android.R.layout.simple_dropdown_item_1line, arrayListOf())
        svSearchOthers.threshold = 0
        svSearchOthers.setAdapter(userSearchAdapter)
        svSearchOthers.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(query: Editable?) {
                Log.d("debug", query.toString())
                presenter.onSearchDispatch(query.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    override fun toString(): String = "searchFragment"

}


class UserSearchAdapter(
    context: Context?, viewResourceId: Int,
    private var items: ArrayList<User>
) : ArrayAdapter<User>(context!!, viewResourceId, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val vi = context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as LayoutInflater
            view = vi.inflate(R.layout.item_search, null)
        }
        val user: User = items[position]
        val fullname = view!!.findViewById<TextView>(R.id.tvFullNameSearch)
        fullname?.setText(user.username)
        val username = view.findViewById<TextView>(R.id.tvUsernameSearch)
        username?.setText(user.username)
        val image = view.findViewById<ImageView>(R.id.ivAvatarUserSearch)
        Glide.with(context).load(user.avatar).into(image)
        return view
    }

    fun updateUser(listUser: List<User>) {
        items.clear()
        items.addAll(listUser)
        notifyDataSetChanged()
        Log.d("debug", count.toString())
    }
}

