package com.example.Pochita.Mainpage

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.Pochita.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.Productclass.Product
import com.example.Pochita.homeFrags.RefilProduct
import com.example.Pochita.recyleViewAdapters.RecAdapter
import com.google.firebase.database.*


class FeedFrag : Fragment(R.layout.fragment_feed) {
    private lateinit var recView_feed: RecyclerView
    private lateinit var itemArrayList_Feed:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded){
            return
        }
        val refill=RefilProduct(requireActivity())

        recView_feed=view.findViewById(R.id.recViewfeed)
        recView_feed.layoutManager= GridLayoutManager(requireActivity(),4,RecyclerView.HORIZONTAL,false)
        recView_feed.setHasFixedSize(true)

        itemArrayList_Feed= arrayListOf()
        val arrayofsection= arrayOf(
            "Shirts",
            "Hats",
            "Trousers",
            "Shoes",
            "Gadgets"
        )
        arrayofsection.forEach {
            refill.getdatafromdatabase(it,itemArrayList_Feed,recView_feed)
        }

        recView_feed.adapter= RecAdapter(requireActivity(),itemArrayList_Feed)

    }

}