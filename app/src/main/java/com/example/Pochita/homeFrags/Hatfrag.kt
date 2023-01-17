package com.example.Pochita.homeFrags

import android.os.Bundle
import android.view.View
import com.example.Pochita.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.Productclass.Product
import com.example.Pochita.recyleViewAdapters.RecAdapter

class Hatfrag : Fragment(R.layout.fragment_hatfrag) {
    private lateinit var recView3:RecyclerView
    private lateinit var itemArrayList3:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) {
            return
        }
        val refill=RefilProduct(requireActivity())

        recView3=view.findViewById(R.id.recView3)
        recView3.layoutManager= LinearLayoutManager(requireActivity())
        recView3.setHasFixedSize(true)

        itemArrayList3= arrayListOf()

        refill.getdatafromdatabase("Hats",itemArrayList3,recView3)

        recView3.adapter= RecAdapter(requireActivity(),itemArrayList3)

    }





}