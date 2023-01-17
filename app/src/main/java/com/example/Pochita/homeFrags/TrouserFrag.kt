package com.example.Pochita.homeFrags

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.Pochita.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.Productclass.Product
import com.example.Pochita.recyleViewAdapters.RecAdapter
import com.google.firebase.database.*

class TrouserFrag : Fragment(R.layout.fragment_trouser) {
    private lateinit var recView2: RecyclerView
    private lateinit var itemArrayList2:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) {
            return
        }
        val refill=RefilProduct(requireActivity())


        recView2=view.findViewById(R.id.recView2)
        recView2.layoutManager=LinearLayoutManager(requireActivity())
        recView2.setHasFixedSize(true)

        itemArrayList2= arrayListOf()

        refill.getdatafromdatabase("Trousers",itemArrayList2,recView2)

        recView2.adapter= RecAdapter(requireActivity(),itemArrayList2)
    }


}