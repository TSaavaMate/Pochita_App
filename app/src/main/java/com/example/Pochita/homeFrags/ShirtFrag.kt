package com.example.Pochita.homeFrags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.Pochita.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.Productclass.Product
import com.example.Pochita.recyleViewAdapters.RecAdapter

class ShirtFrag : Fragment(R.layout.fragment_shirt) {
    private lateinit var recView: RecyclerView
    private lateinit var itemArrayList1:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) {
            return
        }
        val refill=RefilProduct(requireActivity())

        recView=view.findViewById(R.id.recView)
        recView.layoutManager=LinearLayoutManager(requireActivity())
        recView.setHasFixedSize(true)

        itemArrayList1= arrayListOf()


        refill.getdatafromdatabase("Shirts",itemArrayList1,recView)

        recView.adapter= RecAdapter(requireActivity(),itemArrayList1)
    }



}