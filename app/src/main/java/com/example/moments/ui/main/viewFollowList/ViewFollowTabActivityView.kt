package com.example.moments.ui.main.viewFollowList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.moments.R
import com.example.moments.data.model.User
import com.example.moments.ui.main.viewProfile.ProfileFragmentView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_view_follow_list.*

class ViewFollowTabActivityView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_follow_list)
        initFollowLinear()
    }

    private fun initFollowLinear() {
        val followersDataList = arrayListOf<Followers>()
        val urlAvatar =
            "https://firebasestorage.googleapis.com/v0/b/ggrm-2d70b.appspot.com/o/1648308491999%2F1%2Fpic2.png?alt=media&token=22807789-8a01-413d-8ce5-fd86651e5107"
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/ronaldo.jpeg?alt=media&token=aa233a9c-7315-431f-be37-8856986efdf7", "Carter Kris", true))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download.png?alt=media&token=e0cb33be-ce04-41ef-8cf1-bfed0a4f1d2e", "Berenice Leffler", true))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download%20(1).png?alt=media&token=ece35d69-9170-4cca-9c27-cc61c94c7a4d", "Arnold Corwin", false))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download%20(2).png?alt=media&token=3e987ea7-61a1-4d35-bdf1-cb1de77ad401", "Mathias Metz", true))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/default_avatar.png?alt=media&token=0adf8096-f67f-4209-985c-7a5dff38a4d4", "Olin Ferry", false))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168732.png?alt=media&token=9b63f702-91ed-402f-8778-d9ea009bfac1", "Eve Renner", false))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168726.png?alt=media&token=1b124baf-36e5-44d2-98a7-20990f2e5a4d", "Miss Bonita Quitzon", true))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/ronaldo.jpeg?alt=media&token=aa233a9c-7315-431f-be37-8856986efdf7", "Jerrod Howell", true))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download.png?alt=media&token=e0cb33be-ce04-41ef-8cf1-bfed0a4f1d2e", "Ted Marks", true))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download%20(1).png?alt=media&token=ece35d69-9170-4cca-9c27-cc61c94c7a4d", "Brycen Roberts", false))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download%20(2).png?alt=media&token=3e987ea7-61a1-4d35-bdf1-cb1de77ad401", "Helmer Hoppe", true))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/default_avatar.png?alt=media&token=0adf8096-f67f-4209-985c-7a5dff38a4d4", "Brandyn Schoen", false))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168732.png?alt=media&token=9b63f702-91ed-402f-8778-d9ea009bfac1", "Mozelle Hegmann", false))
        followersDataList.add(Followers("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168726.png?alt=media&token=1b124baf-36e5-44d2-98a7-20990f2e5a4d", "Maya Harris", true))

        val followingDataList = arrayListOf<Following>()
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168726.png?alt=media&token=1b124baf-36e5-44d2-98a7-20990f2e5a4d", "Pasquale Lockman", "Pasquale Lockman"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168732.png?alt=media&token=9b63f702-91ed-402f-8778-d9ea009bfac1", "Krista Schowalter", "Krista Schowalter"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/default_avatar.png?alt=media&token=0adf8096-f67f-4209-985c-7a5dff38a4d4", "Dedrick Russel", "Dedrick Russel"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download%20(2).png?alt=media&token=3e987ea7-61a1-4d35-bdf1-cb1de77ad401", "Bella Corkery", "Bella Corkery"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download%20(1).png?alt=media&token=ece35d69-9170-4cca-9c27-cc61c94c7a4d", "Nat Schinner", "Nat Schinner"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download.png?alt=media&token=e0cb33be-ce04-41ef-8cf1-bfed0a4f1d2e", "Zita Prosacco", "Zita Prosacco"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/ronaldo.jpeg?alt=media&token=aa233a9c-7315-431f-be37-8856986efdf7", "Vena Schaden", "Vena Schaden"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168726.png?alt=media&token=1b124baf-36e5-44d2-98a7-20990f2e5a4d", "Berry Kub", "Berry Kub"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/168732.png?alt=media&token=9b63f702-91ed-402f-8778-d9ea009bfac1", "Aida Stroman", "Aida Stroman"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/default_avatar.png?alt=media&token=0adf8096-f67f-4209-985c-7a5dff38a4d4", "D'angelo Watsica", "D'angelo Watsica"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download%20(2).png?alt=media&token=3e987ea7-61a1-4d35-bdf1-cb1de77ad401", "Ellie White", "Ellie White"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download%20(1).png?alt=media&token=ece35d69-9170-4cca-9c27-cc61c94c7a4d", "Aaron Langosh", "Aaron Langosh"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/download.png?alt=media&token=e0cb33be-ce04-41ef-8cf1-bfed0a4f1d2e", "Emilie Olson", "Emilie Olson"))
        followingDataList.add(Following("https://firebasestorage.googleapis.com/v0/b/moments-167ed.appspot.com/o/ronaldo.jpeg?alt=media&token=aa233a9c-7315-431f-be37-8856986efdf7", "Keon Thompson", "Keon Thompson"))

        val intent = intent
        val type = intent.getStringExtra("FollowViewType")?.toInt()
        val user = intent.getParcelableExtra<User>(ProfileFragmentView.USER_KEY)!!

        tbFollowList.setNavigationOnClickListener { finish() }
        tvToolbarTittle.setText(user.username)
        vp2_view_follow.adapter = FollowGridViewPagerAdapter(
            this, followersDataList, followingDataList
        )

        type?.let { vp2_view_follow?.setCurrentItem(it) }
        TabLayoutMediator(tab_layout_view_follow, vp2_view_follow!!) { tab, position ->
            when (position) {
                0 -> tab.text = "${followersDataList.size} Followers"
                1 -> tab.text = "${followingDataList.size} Following"
            }
        }.attach()
    }
}