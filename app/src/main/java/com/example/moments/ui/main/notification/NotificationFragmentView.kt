package com.example.moments.ui.main.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moments.R
import com.example.moments.databinding.ActivityMainBinding
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.ui.main.search.SearchFragmentView
import com.example.moments.ui.main.viewProfile.ImagesAdapter
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationFragmentView : Fragment() {
    companion object {
        fun newInstance(): NotificationFragmentView {
            return NotificationFragmentView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_notification, container, false)
        initRecycleView(view)
        return view
    }

    override fun toString(): String = "notificationFragment"

    fun initRecycleView(view : View?){
        val followList = mutableListOf<NotificationRecyclerViewItem>()
        val urlAvatar = "https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107"
        followList.add(NotificationRecyclerViewItem.followNotification("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/ronaldo.jpeg?alt=media&token=aa233a9c-7315-431f-be37-8856986efdf7","An Duy want to follow you","2w"))
        followList.add(NotificationRecyclerViewItem.commonNotification("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download.png?alt=media&token=e0cb33be-ce04-41ef-8cf1-bfed0a4f1d2e","An Duy want to follow you","2w", "https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/ronaldo.jpeg?alt=media&token=aa233a9c-7315-431f-be37-8856986efdf7"))
        followList.add(NotificationRecyclerViewItem.commonNotification("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168726.png?alt=media&token=1b124baf-36e5-44d2-98a7-20990f2e5a4d","Hoang Long want to follow you","2w",urlAvatar))
        followList.add(NotificationRecyclerViewItem.followNotification(urlAvatar,"Son Tran want to follow you","2h"))
        followList.add(NotificationRecyclerViewItem.commonNotification("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168732.png?alt=media&token=9b63f702-91ed-402f-8778-d9ea009bfac1","An Duy want to follow you","2w","https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168726.png?alt=media&token=1b124baf-36e5-44d2-98a7-20990f2e5a4d"))
        followList.add(NotificationRecyclerViewItem.followNotification(urlAvatar,"Hoang Long","2w"))
        followList.add(NotificationRecyclerViewItem.followNotification("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168732.png?alt=media&token=9b63f702-91ed-402f-8778-d9ea009bfac1","Quang Huy","2w"))

        val recycleView = view?.findViewById<RecyclerView>(R.id.rcNotificationList)
        recycleView?.layoutManager = LinearLayoutManager(activity)
        val adapter = context?.let { NotificationRecyclerViewAdapter(it) }
        adapter?.items = followList
        recycleView?.adapter = adapter

        adapter?.itemClickListener = { view, item, position ->
                val messenger = when(item){
                    is NotificationRecyclerViewItem.commonNotification -> "Common ${item.content} click"
                    is NotificationRecyclerViewItem.followNotification -> "Follow ${item.content} click"
                }
            Toast.makeText(context,messenger,Toast.LENGTH_SHORT).show()
        }

        adapter?.onButtonClick = {view, item, position ->
            val messenger = when(item){
                is NotificationRecyclerViewItem.commonNotification -> "Nothing"
                is NotificationRecyclerViewItem.followNotification -> "Follow ${item.content} click button"
            }
            Toast.makeText(context,messenger,Toast.LENGTH_SHORT).show()
        }
    }
}

