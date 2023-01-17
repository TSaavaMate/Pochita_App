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


class Gadgetfrag : Fragment(R.layout.fragment_gadgetfrag) {
    private lateinit var recView4: RecyclerView
    private lateinit var itemArrayList4:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) {
            return
        }
        val refill=RefilProduct(requireActivity())

        recView4=view.findViewById(R.id.recView4)
        recView4.layoutManager= LinearLayoutManager(requireActivity())
        recView4.setHasFixedSize(true)

        itemArrayList4= arrayListOf()

        refill.getdatafromdatabase("Gadgets",itemArrayList4,recView4)

        recView4.adapter= RecAdapter(requireActivity(),itemArrayList4)

    }




}