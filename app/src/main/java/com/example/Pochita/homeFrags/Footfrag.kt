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


class Footfrag : Fragment(R.layout.fragment_footfrag) {
    private lateinit var recView5: RecyclerView
    private lateinit var itemArrayList5:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) {
            return
        }
        val refill=RefilProduct(requireActivity())


        recView5=view.findViewById(R.id.recView5)
        recView5.layoutManager= LinearLayoutManager(requireActivity())
        recView5.setHasFixedSize(true)

        itemArrayList5= arrayListOf()



        refill.getdatafromdatabase("Shoes",itemArrayList5,recView5)

        recView5.adapter= RecAdapter(requireActivity(),itemArrayList5)

    }





}