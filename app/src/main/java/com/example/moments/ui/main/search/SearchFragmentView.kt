package com.example.moments.ui.main.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moments.R
import com.example.moments.data.model.Post
import com.example.moments.data.model.User
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.customClasses.IOnRecyclerViewItemTouchListener
import com.example.moments.ui.main.viewOtherProfile.OtherProfileActivityView
import com.example.moments.ui.main.viewPost.ViewPostActivityView
import com.example.moments.ui.main.viewProfile.ImagesAdapter
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject
import kotlin.math.min


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
        initSearchingBar()
        initGridView()
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun onSearchResultCallback(listUser: List<User>) {
        // reset the adapter with new callback result
        this.listUser = listUser
        val adapter = UserSearchAdapter(context, android.R.layout.simple_dropdown_item_1line, this.listUser)
        svSearchOthers.setAdapter(adapter)
        svSearchOthers.showDropDown()
    }

    override fun updateGrid(listPosts: List<Post>) {
        listPost = listPosts
        listPost.forEach {
            x -> dataList.add(x.listMedia[0])
        }
        val adapter = rvOthersList.adapter as ImagesAdapter
    }

    override fun setUp() {

    }

    private lateinit var listPost: List<Post>
    private val dataList: ArrayList<String> = fakeData()
    private var loading: Boolean = true
    private fun initGridView(){
        rvOthersList.adapter = ImagesAdapter(requireContext(), ArrayList(dataList.subList(0,20)),
            object:IOnRecyclerViewItemTouchListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(requireContext(), ViewPostActivityView::class.java)
                    intent.putExtra("postId", listPost[position].id)
                    startActivity(intent)
                }
            })
        rvOthersList.layoutManager = GridLayoutManager(context, 3)
        rvOthersList.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val mLayoutManager = rvOthersList.layoutManager as GridLayoutManager
                    val visibleItemCount = mLayoutManager.childCount
                    val totalItemCount = mLayoutManager.itemCount
                    val pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            // Do pagination.. i.e. fetch new data
                            loadMore(totalItemCount)
                            loading = true;
                        }
                    }
                }
            }
        })
    }

    private fun loadMore(total: Int) {
        val fetch = min(6, dataList.size - total)
        (rvOthersList.adapter as ImagesAdapter).updateItems(
            dataList.subList(
                total,
                total + fetch
            )
        )
    }

    private lateinit var listUser: List<User>
    private fun initSearchingBar(){
        listUser = listOf()
        val userSearchAdapter =
            UserSearchAdapter(context, android.R.layout.simple_dropdown_item_1line, listUser)
        svSearchOthers.threshold = 0
        svSearchOthers.setAdapter(userSearchAdapter)
        svSearchOthers.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(query: Editable?) {
                //Log.d("debug", query.toString())
                presenter.onSearchDispatch(query.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        svSearchOthers.setOnItemClickListener { _, _, i, l ->
            val intent = Intent(activity, OtherProfileActivityView::class.java)
            intent.putExtra("USER_ID", listUser[i].id)
            startActivity(intent)
        }
    }

    override fun toString(): String = "searchFragment"

    private fun fakeData(): ArrayList<String>{
        val imageList = arrayListOf<String>()
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic3.png?alt=media&token=272b593d-eb42-4251-b88f-10c4aae740b3")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic3.png?alt=media&token=272b593d-eb42-4251-b88f-10c4aae740b3")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic3.png?alt=media&token=272b593d-eb42-4251-b88f-10c4aae740b3")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic3.png?alt=media&token=272b593d-eb42-4251-b88f-10c4aae740b3")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic3.png?alt=media&token=272b593d-eb42-4251-b88f-10c4aae740b3")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic3.png?alt=media&token=272b593d-eb42-4251-b88f-10c4aae740b3")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        imageList.add("https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F2%2Fpic1.png?alt=media&token=8528742c-f2e9-4c80-8b56-4b07d13f4bf1")
        return imageList
    }
}


class UserSearchAdapter(
    context: Context?, viewResourceId: Int,
    private var items: List<User>
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
        fullname?.text = user.username
        val username = view.findViewById<TextView>(R.id.tvUsernameSearch)
        username?.text = user.username
        val image = view.findViewById<ImageView>(R.id.ivAvatarUserSearch)
        Glide.with(context).load(user.avatar).into(image)
        return view
    }

    fun updateUser(listUser: List<User>) {
        this.clear()
        for(i: Int in listUser.indices){
            this.insert(listUser[i], i)
        }
        setNotifyOnChange(true)
        notifyDataSetChanged()
        Log.d("debug", count.toString())
    }
}

