package com.example.moments.ui.main.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moments.R
import com.example.moments.data.model.RetrievedNotification
import com.example.moments.ui.base.BaseFragment
import javax.inject.Inject

class NotificationFragmentView : BaseFragment(), INotificationView {
    val followList = mutableListOf<NotificationRecyclerViewItem>()
    companion object {
        fun newInstance(): NotificationFragmentView {
            return NotificationFragmentView()
        }
    }
    @Inject
    internal lateinit var presenter: INotificationPresenter<INotificationView, INotificationInteractor>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.activity_notification, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycleView(view)
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUp() {
        presenter.onViewPrepared()
    }

    override fun updateList(list: List<RetrievedNotification>) {
        dataList = list
        adapter.updateList(dataList)
    }

    override fun toString(): String = "notificationFragment"

    private lateinit var dataList: List<RetrievedNotification>
    private lateinit var adapter: NotificationRecyclerViewAdapter
    private fun initRecycleView(view : View?){

        val recycleView = view?.findViewById<RecyclerView>(R.id.rcNotificationList)
        recycleView?.layoutManager = LinearLayoutManager(activity)
        adapter = context?.let { NotificationRecyclerViewAdapter(it) }!!
        adapter.items = followList
        recycleView?.adapter = adapter

        adapter.itemClickListener = { view, item, position ->
                val messenger = when(item){
                    is NotificationRecyclerViewItem.commonNotification -> "Common ${item.content} click"
                    is NotificationRecyclerViewItem.followNotification -> "Follow ${item.content} click"
                }
            Toast.makeText(context,messenger,Toast.LENGTH_SHORT).show()
        }

        adapter.onButtonClick = { view, item, position ->
            val messenger = when(item){
                is NotificationRecyclerViewItem.commonNotification -> "Nothing"
                is NotificationRecyclerViewItem.followNotification -> "Follow ${item.content} click button"
            }
            Toast.makeText(context,messenger,Toast.LENGTH_SHORT).show()
        }
    }
}

