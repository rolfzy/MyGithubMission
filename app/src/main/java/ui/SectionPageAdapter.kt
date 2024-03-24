package ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {



    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = userFollowFragment()
        fragment.arguments = Bundle().apply {
            putString(userFollowFragment.ARG_USERNAME, username)
            putInt(userFollowFragment.ARG_POSITION, position + 1)
        }

        return fragment
    }
    override fun getItemCount(): Int = 2

}