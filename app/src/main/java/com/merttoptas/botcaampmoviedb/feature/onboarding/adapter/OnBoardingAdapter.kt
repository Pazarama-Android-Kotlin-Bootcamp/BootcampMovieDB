package com.merttoptas.botcaampmoviedb.feature.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.merttoptas.botcaampmoviedb.feature.onboarding.fragment.OnBoardingFragment

/**
 * Created by merttoptas on 29.10.2022.
 */

class OnBoardingAdapter(
    fragmentActivity: FragmentActivity,
    private val layouts: List<Int>
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return layouts.size
    }

    override fun createFragment(position: Int): Fragment {
        return OnBoardingFragment.newInstance(layouts[position])
    }

}