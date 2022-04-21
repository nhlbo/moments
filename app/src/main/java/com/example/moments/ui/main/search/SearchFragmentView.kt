package com.example.moments.ui.main.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.User


class SearchFragmentView : Fragment(R.layout.activity_search) {
    companion object {
        fun newInstance(): SearchFragmentView {
            return SearchFragmentView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_search, container, false)
        search(view)
        return view
    }

    override fun toString(): String = "searchFragment"

    fun search(view : View){
        val atctv : AutoCompleteTextView = view.findViewById(R.id.svSearchOthers)
        val userlist = arrayListOf<User>()
        userlist.add(User("1","Anduy","abc@gmail.com"))
        userlist.add(User("2","Anhduy","abc1@gmail.com"))
        userlist.add(User("3","HoangLong","abc2@gmail.com"))
        userlist.add(User("4","XuanSon","abc3@gmail.com"))
        userlist.add(User("5","QuangHuy","abc4@gmail.com"))
        Log.i("checkUser",userlist.toString())

        val adapter =
            UserSearchAdapter(context, android.R.layout.simple_dropdown_item_1line, userlist)
        atctv.setAdapter(adapter)
        atctv!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

}




class UserSearchAdapter(
    context: Context?, viewResourceId: Int,
    items: ArrayList<User>
) : ArrayAdapter<User?>(context!!, viewResourceId, items as List<User?>) {
    private val items: ArrayList<User> = items
    private val itemsAll: ArrayList<User> = items.clone() as ArrayList<User>
    private val suggestions : ArrayList<User> = ArrayList<User>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = convertView
        if (v == null) {
            val vi = context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as LayoutInflater
            v = vi.inflate(R.layout.item_search, null)
        }
        val user: User = items[position]
        if (user != null) {
            val fullname = v!!.findViewById<TextView>(R.id.tvFullNameSearch)
            fullname?.setText(user.username)
            val username = v!!.findViewById<TextView>(R.id.tvUsernameSearch)
            username?.setText(user.username)
            val image =  v!!.findViewById<ImageView>(R.id.ivAvatarUserSearch)
            Glide.with(context).load(user.avatar).into(image)
        }
        return v!!
    }

    override fun getFilter(): Filter {
        return nameFilter
    }

    var nameFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): String {
            return (resultValue as User).username
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return if (constraint != null) {
                suggestions.clear()
                for (user in itemsAll) {
                    if (user.username.lowercase()
                            .startsWith(constraint.toString().lowercase())
                    ) {
                        suggestions.add(user)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = suggestions
                filterResults.count = suggestions.size
                filterResults
            } else {
                FilterResults()
            }
        }

        override fun publishResults(
            constraint: CharSequence?,
            results: FilterResults?
        ) {

            if (results != null && results.count > 0) {
                clear()
                val filteredList: ArrayList<User> =
                    results!!.values as ArrayList<User>
                for (c in filteredList) {
                    add(c)
                }
                notifyDataSetChanged()
            }
        }
    }


}

