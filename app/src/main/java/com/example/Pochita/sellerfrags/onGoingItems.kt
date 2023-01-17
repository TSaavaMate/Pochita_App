package com.example.Pochita.sellerfrags

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.Productclass.Product
import com.example.Pochita.R
import com.example.Pochita.recyleViewAdapters.RecAdapterForReviewedItems
import com.example.Pochita.recyleViewAdapters.RecAdapterForShipped
import com.google.firebase.database.*
import kotlin.math.log


class onGoingItems : Fragment(R.layout.fragment_on_going_items) {
    private lateinit var recViewOnGoing:RecyclerView
    private lateinit var itemAraayOnGoing:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recViewOnGoing=view.findViewById(R.id.recViewOfOngoing)
        recViewOnGoing.layoutManager= LinearLayoutManager(requireActivity())
        recViewOnGoing.setHasFixedSize(true)

        itemAraayOnGoing= arrayListOf()


        recViewOnGoing.adapter= RecAdapterForReviewedItems(requireActivity(),itemAraayOnGoing)

    }



}