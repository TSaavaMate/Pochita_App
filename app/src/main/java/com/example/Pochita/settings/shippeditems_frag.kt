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
import com.example.Pochita.recyleViewAdapters.RecAdapterForShipped
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class shippeditems_frag : Fragment(R.layout.fragment_shippeditems_frag) {
    private lateinit var recviewShipped:RecyclerView
    private lateinit var itemArrayListShipped:ArrayList<Product>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recviewShipped=view.findViewById(R.id.recViewForShippedItems)
        recviewShipped.layoutManager=LinearLayoutManager(requireActivity())
        recviewShipped.setHasFixedSize(true)

        itemArrayListShipped= arrayListOf()
        getDataFromDatabase()

        recviewShipped.adapter=RecAdapterForShipped(requireActivity(),itemArrayListShipped)
    }

    private fun getDataFromDatabase() {
        val typeIndicator = object : GenericTypeIndicator<HashMap<String, Any>>() {}

        val database = FirebaseDatabase.getInstance().getReference("Shipped products")
        val currentUser= FirebaseAuth.getInstance().currentUser
        database.child("User:${currentUser?.uid}")
            .addChildEventListener(object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if (!isAdded) {
                        return
                    }
                    val product = Product()
                    val data = snapshot.getValue(typeIndicator)
                    Log.d(ContentValues.TAG, "in onChildadded ${data}")
                    data?.forEach {
                        try {

                            when (it.key) {
                                "imageId" -> product.imageId = it.value as String
                                "shipping" -> product.shipping = it.value as String
                                "price" -> product.price = it.value as Double
                                "rating" -> product.rating = it.value as Double
                                "description" -> product.description = it.value as String
                            }
                        } catch (e: Exception) {
                            when (it.key) {
                                "imageId" -> product.imageId = it.value as String
                                "shipping" -> product.shipping = it.value as String
                                "price" -> product.price = (it.value as Long).toDouble()
                                "rating" -> product.rating = (it.value as Long).toDouble()
                                "description" -> product.description = it.value as String
                            }
                        }
                    }
                    itemArrayListShipped.add(product)
                    val adapter = recviewShipped.adapter as RecAdapterForShipped
                    adapter.notifyDataSetChanged()
                    recviewShipped.adapter= RecAdapterForShipped(requireActivity(),itemArrayListShipped)
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