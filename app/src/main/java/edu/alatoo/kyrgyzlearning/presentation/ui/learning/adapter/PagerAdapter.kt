package edu.alatoo.kyrgyzlearning.presentation.ui.learning.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private var mFragmentList = arrayListOf<Fragment>()

    private val mFragmentTitleList = arrayListOf<String>()


    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun addFragment(fragment: Fragment, fragmentTitle: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(fragmentTitle)
    }


    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    fun getTabTitle(position: Int): String {
        return mFragmentTitleList[position]
    }

}