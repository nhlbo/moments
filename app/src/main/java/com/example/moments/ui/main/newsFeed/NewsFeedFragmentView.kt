package com.example.moments.ui.main.newsFeed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moments.R
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.main.message.MessageActivityView
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_news_feed.*
import javax.inject.Inject

class NewsFeedFragmentView : BaseFragment(), INewsFeedView {

    companion object {
        fun newInstance(): NewsFeedFragmentView {
            return NewsFeedFragmentView()
        }
    }

    @Inject
    internal lateinit var presenter: INewsFeedPresenter<INewsFeedView, INewsFeedInteractor>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_news_feed, container, false)
        initRecycleView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
        toolBar = getView()?.findViewById(R.id.newsfeed_header_bar)
        toolBar?.inflateMenu(R.menu.header_newsfeeds)
        onItemSelected()
    }
    private fun initRecycleView(view: View){
        val dataList = arrayListOf<NewsFeed>()
        val imageList = arrayListOf<String>()
        val url1 =
            "https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107"
        val url2 =
            "https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic1.png?alt=media&token=4fdd9b9a-5109-4f88-8ac7-82f47a1c0f68"
        imageList.add(url1)
        imageList.add(url2)
        imageList.add(url2)
        imageList.add(url1)
        imageList.add(url1)
        dataList.add(NewsFeed("AnDuy",url1,"HCM","test newsfeed view",imageList,2,url2,"1 day ago"))
        dataList.add(NewsFeed("SonTran",url2,"Ha Noi","test newsfeed view",imageList,2,url1,"1 day ago"))
        dataList.add(NewsFeed("HoangLong",url2,"HCM","test newsfeed view",imageList,2,url1,"1 day ago"))
        dataList.add(NewsFeed("HuyQuang",url1,"VietNam","test newsfeed view",imageList,2,url2,"1 day ago"))
        dataList.add(NewsFeed("AnDuy",url1,"HCM","test newsfeed view",imageList,2,url2,"1 day ago"))
        newsfeed_header_bar.inflateMenu(R.menu.header_newsfeeds)
        setUpOnClicked()

        recyclerView = view.findViewById(R.id.rcNewsfeedPanel)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val adapter = context?.let { NewsFeedAdapter(it,dataList,this) }

        recyclerView?.adapter = adapter
        recyclerView?.isNestedScrollingEnabled = false

        recyclerView?.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> recyclerView?.parent
                        ?.requestDisallowInterceptTouchEvent(true)
                }
                return false
            }
        }
    }

    override fun setUp() {
        presenter.onViewPrepared()
    }

    override fun updatePost(listPost: List<DocumentSnapshot>) {
        TODO("Not yet implemented")
    }

    override fun toString(): String = "newsfeedFragment"

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    private fun setUpOnClicked() {
        newsfeed_header_bar.setOnMenuItemClickListener {
            when(it.itemId){
            override fun onTouchEvent(view: RecyclerView, event: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            })
         }
    }
    private fun onItemSelected() {
        toolBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.storyBtn -> {
                    // Navigate to settings screen

                    true
                }
                R.id.msgBtn -> {
                    // Save profile changes
                    //findNavController().navigate(R.id.chatFragmentView)
                    val intent: Intent = Intent(activity, MessageActivityView::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onItemTouch(position: Int, command:String) {
        if(command == "showComment"){
            val request = NavDeepLinkRequest.Builder
                .fromUri(R.string.commentFragment.toString().toUri())
                .build()
            findNavController().navigate(R.id.commentFragmentView)
        }
    }

//    private fun switchFragment(fragment: Fragment){
//        activity?.supportFragmentManager?.beginTransaction()!!
//            .replace(R.id.navigateFragmentsContainer, fragment,fragment.toString())
//            .addToBackStack(fragment.toString())
//            .commit()
//    }

    //override fun toString(): String = "newsfeedFragment"
}