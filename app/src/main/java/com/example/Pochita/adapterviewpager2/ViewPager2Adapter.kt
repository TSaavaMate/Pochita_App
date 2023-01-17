package com.example.Pochita.adapterviewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.Pochita.homeFrags.*

class ViewPager2Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                ShirtFrag()
            }
            1->{
                TrouserFrag()
            }
            2->{
                Hatfrag()
            }
            3->{
                Gadgetfrag()
            }
            4->{
                Footfrag()
            }

            else -> {
                Fragment()
            }
        }
    }
}