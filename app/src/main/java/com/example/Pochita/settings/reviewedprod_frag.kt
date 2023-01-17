package com.example.Pochita.settings

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.Productclass.Product
import com.example.Pochita.R
import com.example.Pochita.recyleViewAdapters.RecAdapterForReviewedItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class reviewedprod_frag : Fragment(R.layout.fragment_reviewedprod_frag) {
    private lateinit var recViewReviewed:RecyclerView
    private lateinit var itemArrayForReview:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recViewReviewed=view.findViewById(R.id.recViewOfReview)
        recViewReviewed.layoutManager=LinearLayoutManager(requireActivity())
        recViewReviewed.setHasFixedSize(true)

        itemArrayForReview= arrayListOf()

        getDataFromDatabase()

        recViewReviewed.adapter=RecAdapterForReviewedItems(requireActivity(),itemArrayForReview)
    }

    private fun getDataFromDatabase() {
        val typeIndicator = object : GenericTypeIndicator<HashMap<String, Any>>() {}

        val database = FirebaseDatabase.getInstance().getReference("Reviewed_Products")
        val currentUser= FirebaseAuth.getInstance().currentUser
        database.child("User:${currentUser?.uid}")
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
                    itemArrayForReview.add(cart)
                    val adapter = recViewReviewed.adapter as RecAdapterForReviewedItems
                    adapter.notifyDataSetChanged()
                    recViewReviewed.adapter= RecAdapterForReviewedItems(requireActivity(),itemArrayForReview)
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