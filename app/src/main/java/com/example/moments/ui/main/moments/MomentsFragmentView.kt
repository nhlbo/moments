package com.example.moments.ui.main.moments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.moments.R
import com.example.moments.data.model.MomentsData
import com.example.moments.ui.main.comment.CommentFragmentView

class MomentsFragmentView : Fragment() {
    companion object {
        fun newInstance(): MomentsFragmentView {
            return MomentsFragmentView()
        }
    }

    private lateinit var recyclerViewContainer: RecyclerView
    private lateinit var recyclerViewAdapter: MomentsRecyclerViewAdapter
    private lateinit var commentContainerView: FragmentContainerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_moments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        commentContainerView = view.findViewById(R.id.commentMomentsFragmentContainer)
    }

    private fun initRecyclerView(view: View) {
        recyclerViewContainer = view.findViewById(R.id.rcMomentsContainer)
        val videos = fakeData()
        recyclerViewAdapter = MomentsRecyclerViewAdapter(videos,
            object : MomentsButtonClickListener {
                override fun onPositionClicked(sender: ClickedButton, position: Int) {
                    when (sender) {
                        ClickedButton.COMMENT -> {
                            if (commentContainerView.visibility == View.VISIBLE) {
                                commentContainerView.visibility = View.GONE
                            } else {
                                commentContainerView.visibility = View.VISIBLE
                                commentContainerView.getFragment<CommentFragmentView>()
                                    .findNavController()
                                    .navigate(R.string.commentFragment.toString())
                                activity?.supportFragmentManager!!.beginTransaction().replace(
                                    R.id.commentMomentsFragmentContainer,
                                    CommentFragmentView()
                                ).addToBackStack("commentFragmentView").commit()
                            }

                        }
                        else -> return
                    }
                }
            })
        recyclerViewContainer.layoutManager = LinearLayoutManager(this.context)
        recyclerViewContainer.adapter = recyclerViewAdapter
        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(recyclerViewContainer)
    }

    private fun fakeData(): ArrayList<MomentsData> {
        val res = arrayListOf<MomentsData>()
        res.add(
            MomentsData(
                user_id = 1,
                post_id = 1,
                content = "askdljljqwe",
                audio = "Fake Love",
                likes = 100,
                video = "https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/videos%2F3.mp4?alt=media&token=511cd55c-c30a-46e7-81c2-eb048232f1ee"
            )
        )
        res.add(
            MomentsData(
                user_id = 1,
                post_id = 1,
                content = "askdljljqwe",
                audio = "Fake Love",
                likes = 100,
                video = "https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/videos%2F2.mp4?alt=media&token=1e1201d4-f6a5-4873-8714-94d8eb7cf8ad"
            )
        )
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

    override fun toString(): String = "momentFragment"
}