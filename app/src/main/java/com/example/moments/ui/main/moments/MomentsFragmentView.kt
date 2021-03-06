package com.example.moments.ui.main.moments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.moments.R
import com.example.moments.data.model.MomentsData
import com.example.moments.data.model.RetrievedMoment
import com.example.moments.ui.base.BaseFragment
import com.example.moments.ui.main.comment.CommentActivityView
import com.example.moments.ui.main.viewOtherProfile.OtherProfileActivityView
import javax.inject.Inject

class MomentsFragmentView : BaseFragment(), IMommentsView {
    companion object {
        fun newInstance(): MomentsFragmentView {
            return MomentsFragmentView()
        }
    }

    private lateinit var recyclerViewContainer: RecyclerView
    private lateinit var recyclerViewAdapter: MomentsRecyclerViewAdapter
    private lateinit var commentContainerView: FragmentContainerView

    @Inject
    internal lateinit var presenter: IMommentsPresenter<IMommentsView, IMommentsInteractor>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_moments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
        //commentContainerView = view.findViewById(R.id.commentMomentsFragmentContainer)
    }

    override fun setMenuVisibility(isVisibleToUser: Boolean) {
        super.setMenuVisibility(isVisibleToUser)

        val video = findCurrentVideo()
        if(isVisibleToUser){
            video?.start()
        }else video?.pause()
        currentVisible = isVisibleToUser
    }

    override fun setUp() {
        initRecyclerView(requireView())
        presenter.onViewPrepared()
    }

    private lateinit var videos: ArrayList<MomentsData>
    private var currentVisible = false
    private fun initRecyclerView(view: View) {
        recyclerViewContainer = view.findViewById(R.id.rcMomentsContainer)
        videos = fakeData()
        recyclerViewAdapter = MomentsRecyclerViewAdapter(videos,
            object : MomentsButtonClickListener {
                override fun onPositionClicked(sender: ClickedButton, position: Int) {
                    when (sender) {
                        ClickedButton.COMMENT -> {
                            val intent = Intent(requireContext(), CommentActivityView::class.java)
                            intent.putExtra("postId", videos[position].post_id)
                            startActivity(intent)
                        }
                        ClickedButton.LIKE -> presenter.onLikeMoment(videos[position].post_id)
                        ClickedButton.UNLIKE -> presenter.onUnlikeMoment(videos[position].post_id)
                        ClickedButton.VIEW_PROFILE ->{
                            val intent = Intent(requireContext(), OtherProfileActivityView::class.java)
                            intent.putExtra("USER_ID", videos[position].user_id)
                            startActivity(intent)
                        }
                        else -> return
                    }
                }
            })
        recyclerViewContainer.layoutManager = LinearLayoutManager(this.context)
        recyclerViewContainer.adapter = recyclerViewAdapter

        recyclerViewContainer.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView:RecyclerView , dx:Int,  dy:Int) {
                super.onScrolled(recyclerView, dx, dy)

                val video = findCurrentVideo()
                if(video?.isPlaying == false && currentVisible) video.start()
            }
        })

        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(recyclerViewContainer)
    }

    private fun findCurrentVideo():VideoView?{
        val layoutManager = (recyclerViewContainer.layoutManager as LinearLayoutManager)
        val position = layoutManager.findFirstCompletelyVisibleItemPosition()
        val view = layoutManager.findViewByPosition(position)
        return view?.findViewById(R.id.vvMoments)
    }

    private fun fakeData(): ArrayList<MomentsData> {
        val res = arrayListOf<MomentsData>()
//        res.add(
//            MomentsData(
//                user_id = "1",
//                user_ava = "1",
//                username = "",
//                post_id = "1",
//                content = "askdljljqwe",
//                audio = "Fake Love",
//                likes = 100,
//                video = "https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/videos%2F3.mp4?alt=media&token=511cd55c-c30a-46e7-81c2-eb048232f1ee",
//                createAt = "1s ago"
//            )
//        )
//        res.add(
//            MomentsData(
//                user_id = "1",
//                user_ava = "1",
//                username = "",
//                post_id = "1",
//                content = "askdljljqwe",
//                audio = "Fake Love",
//                likes = 100,
//                video = "https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/videos%2F2.mp4?alt=media&token=1e1201d4-f6a5-4873-8714-94d8eb7cf8ad",
//                createAt = "1s ago"
//            )
//        )
//        res.add(
//            MomentsData(
//            user_id = 1,
//            post_id = 1,
//            content = "askdljljqwe",
//            audio ="Fake Love",
//            likes = 100,
//            video = ""
//        )
//        )
//        res.add(
//            MomentsData(
//            user_id = 1,
//            post_id = 1,
//            content = "askdljljqwe",
//            audio ="Fake Love",
//            likes = 100,
//            video = ""
//        )
//        )
//        res.add(
//            MomentsData(
//            user_id = 1,
//            post_id = 1,
//            content = "askdljljqwe",
//            audio ="Fake Love",
//            likes = 100,
//            video = ""
//        )
//        )
//        res.add(
//            MomentsData(
//            user_id = 1,
//            post_id = 1,
//            content = "askdljljqwe",
//            audio ="Fake Love",
//            likes = 100,
//            video = ""
//        )
//        )
        return res
    }

    override fun getListMoment(list: List<RetrievedMoment>) {
        //videos.clear()
        for (item in list){
            videos.add(MomentsData(
                user_id = item.creator.id,
                user_ava = item.creator.avatar,
                username = item.creator.username,
                post_id = item.id,
                content = item.caption,
                audio = "",
                likes = item.likeCount,
                video = item.media,
                createAt = item.createdAt.toDate().toString()
            ))
        }
        recyclerViewAdapter.notifyItemRangeInserted(recyclerViewContainer.childCount, list.size)
    }

    override fun toString(): String = "momentFragment"
}