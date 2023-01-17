package com.example.Pochita.Mainpage

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.Pochita.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.Productclass.Product
import com.example.Pochita.recyleViewAdapters.RecAdapterForCart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CartFrag : Fragment(R.layout.fragment_cart) {
    private lateinit var recViewcart: RecyclerView
    private lateinit var itemArrayListcart:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isAdded) {
            return
        }
        itemArrayListcart= arrayListOf()
        recViewcart=view.findViewById(R.id.recviewofcart)
        recViewcart.layoutManager= LinearLayoutManager(requireActivity())
        recViewcart.setHasFixedSize(true)
        val typeIndicator = object : GenericTypeIndicator<HashMap<String, Any>>() {}
        val database = FirebaseDatabase.getInstance().getReference("Carts")
        val CurrentUser= FirebaseAuth.getInstance().currentUser
        database.child("User:${CurrentUser?.uid}")
            .addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (!isAdded) {
                    return
                }
                val cart = Product()
                val data = snapshot.getValue(typeIndicator)
                Log.d(TAG, "in onChildadded ${data}")
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
                itemArrayListcart.add(cart)
                val adapter = recViewcart.adapter as RecAdapterForCart
                adapter.notifyDataSetChanged()
                recViewcart.adapter= RecAdapterForCart(requireActivity(),itemArrayListcart)
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
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        Log.d(TAG, "this is log at last ${itemArrayListcart}")
        recViewcart.adapter= RecAdapterForCart(requireActivity(),itemArrayListcart)

    }


}

