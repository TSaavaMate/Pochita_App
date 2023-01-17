package com.example.Pochita.Mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.Pochita.R
import com.example.Pochita.adapterviewpager2.ViewPager2Adapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFrag : Fragment(R.layout.fragment_home) {
    private lateinit var tabLayout:TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout=view.findViewById(R.id.tabLayout)
        viewPager=view.findViewById(R.id.viewPager)
        viewPager.adapter = ViewPager2Adapter(childFragmentManager, lifecycle)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position){
                0->{tab.setIcon(R.drawable.shirt_icon)}
                1->{tab.setIcon(R.drawable.trousers_icon)}
                2->{tab.setIcon(R.drawable.hat_icon)}
                3->{tab.setIcon(R.drawable.gadget_icon)}
                4->{tab.setIcon(R.drawable.shoe_icon)}
            }
        }.attach()

    }
}