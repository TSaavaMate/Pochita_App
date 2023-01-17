package com.example.Pochita.settings

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import com.example.Pochita.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.Productclass.Product
import com.example.Pochita.recyleViewAdapters.RecAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class wishlist_frag : Fragment(R.layout.fragment_wishlist_frag) {
    private lateinit var recViewWish:RecyclerView
    private lateinit var itemArrayListWish:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recViewWish=view.findViewById(R.id.recViewWishList)
        recViewWish.layoutManager=LinearLayoutManager(requireActivity())
        recViewWish.setHasFixedSize(true)

        itemArrayListWish= arrayListOf()
        getdata()

        recViewWish.adapter=RecAdapter(requireActivity(),itemArrayListWish)

    }

    private fun getdata() {
        val typeIndicator = object : GenericTypeIndicator<HashMap<String, Any>>() {}

        val database = FirebaseDatabase.getInstance().getReference("Wish_list")
        val Curentuser=FirebaseAuth.getInstance().currentUser
        database.child("User:${Curentuser?.uid}")
            .addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (!isAdded) {
                    return
                }
                val cart = Product()
                val data = snapshot.getValue(typeIndicator)
                Log.d(ContentValues.TAG, "in onChildadded ${data}")
                data?.forEach {
                    try {

                        when (it.key) {
                            "imageId" -> cart.imageId = it.value as String
                            "shipping" -> cart.shipping = it.value as String
                            "price" -> cart.price = it.value as Double
                            "rating" -> cart.rating = it.value as Double
                            "description" -> cart.description = it.value as String
                        }
                    } catch (e: Exception) {
                        when (it.key) {
                            "imageId" -> cart.imageId = it.value as String
                            "shipping" -> cart.shipping = it.value as String
                            "price" -> cart.price = (it.value as Long).toDouble()
                            "rating" -> cart.rating = (it.value as Long).toDouble()
                            "description" -> cart.description = it.value as String
                        }
                    }
                }
                itemArrayListWish.add(cart)
                val adapter = recViewWish.adapter as RecAdapter
                adapter.notifyDataSetChanged()
                recViewWish.adapter= RecAdapter(requireActivity(),itemArrayListWish)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Update the data in the itemArrayListcart and update the RecyclerView adapter
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // Remove the data from the itemArrayListcart and update the RecyclerView adapter
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Update the order of the items in the itemArrayListcart and update the RecyclerView adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }
}