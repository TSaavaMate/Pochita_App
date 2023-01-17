package com.example.Pochita.homeFrags

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.Productclass.Product
import com.example.Pochita.recyleViewAdapters.RecAdapter
import com.google.firebase.database.*

open class RefilProduct(private val context: Context) {

    open fun getdatafromdatabase(section:String, itemArrayList:ArrayList<Product>, recView: RecyclerView) {
        val typeIndicator = object : GenericTypeIndicator<HashMap<String, Any>>() {}

        val database = FirebaseDatabase.getInstance().getReference("Products")
        database.child(section)
            .addChildEventListener(object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
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
                    itemArrayList.add(product)
                    val adapter = recView.adapter as RecAdapter
                    adapter.notifyDataSetChanged()
                    recView.adapter= RecAdapter(context,itemArrayList)
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