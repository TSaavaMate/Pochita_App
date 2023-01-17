package com.example.Pochita.sellerfrags

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.example.Pochita.R
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.Productclass.Product
import com.example.Pochita.recyleViewAdapters.RecAdapterForSeller
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class magazinefrag : Fragment(R.layout.fragment_magazinefrag) {
    private lateinit var recviewSeller:RecyclerView
    private lateinit var magazinename:TextView
    private lateinit var itemArrayforseller:ArrayList<Product>
    private lateinit var fab:FloatingActionButton
    private lateinit var database: DatabaseReference
    private lateinit var sharedPref: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setmagazinename(view)
        //
        itemArrayforseller= arrayListOf()

        recviewSeller=view.findViewById(R.id.recViewseller)
        recviewSeller.layoutManager=LinearLayoutManager(requireActivity())
        recviewSeller.setHasFixedSize(true)

//        getdatafromdatabase()


        recviewSeller.adapter= RecAdapterForSeller(requireActivity(),itemArrayforseller)

        //opens alert dialog and reads data
        fab=view.findViewById(R.id.floatingActionButton)
        fab.setOnClickListener{
            view.findViewById<ImageView>(R.id.add_product_on_magazine).visibility=View.GONE
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("adding product")
            builder.setMessage("enter product's data")
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.alertdialog_forseller, null)
            builder.setView(dialogView)
            val image = dialogView.findViewById<EditText>(R.id.imageurl)
            val price = dialogView.findViewById<EditText>(R.id.price)
            val description = dialogView.findViewById<EditText>(R.id.description)
            val ratinG = dialogView.findViewById<EditText>(R.id.ratinG)
            val shipping_time = dialogView.findViewById<EditText>(R.id.shipping_time)
            val Section = dialogView.findViewById<EditText>(R.id.section)
            val magName=dialogView.findViewById<TextView>(R.id.magazinename)

            builder.setPositiveButton("Add") { _, _ ->
                //checks if some fields are empty
                when{
                    TextUtils.isEmpty(image.text.toString())->{image.error="type url"
                        return@setPositiveButton}
                    TextUtils.isEmpty(price.text.toString())->{price.error="type price"
                        return@setPositiveButton}
                    TextUtils.isEmpty(description.text.toString())->{description.error="type description"
                        return@setPositiveButton}
                    TextUtils.isEmpty(ratinG.text.toString())->{ratinG.error="type ratinG"
                        return@setPositiveButton}
                    TextUtils.isEmpty(shipping_time.text.toString())->{shipping_time.error="type shipping_time"
                        return@setPositiveButton}
                    TextUtils.isEmpty(Section.text.toString())->{Section.error="type Section name"
                        return@setPositiveButton}
                }



                //sets magazine name on first layout with sharedpref
                    if (magName.text.toString().length>5){
                        sharedPref=requireActivity().getSharedPreferences("mypref", MODE_PRIVATE)
                        val editor=sharedPref.edit()
                        val Name=magName.text.toString()
                        editor.putString("key", Name)
                        editor.apply()
                        magazinename.text=magName.text.toString()
                    }


                //gets data from dialog and puts in product class
                    val imageurl_item = image.text.toString()
                    val price_item = price.text.toString().toDouble()
                    val description_item = description.text.toString()
                    val rating = ratinG.text.toString().toDouble()
                    val shipping_time_item = shipping_time.text.toString()
                    val section=Section.text.toString()

                    val product=Product(imageurl_item,price_item,rating,description_item,shipping_time_item)




                //adding items on database
                    when{
                        section=="Shirts"->{
                            database= FirebaseDatabase.getInstance().getReference("Products")
                            val newSectionRef = database.child("Shirts").push()
                            newSectionRef.setValue(product)
                                .addOnSuccessListener {
                                    Log.d(ContentValues.TAG, "succes in Shirts")
                                }
                                .addOnFailureListener {
                                    Log.d(ContentValues.TAG, "fail in Shirts")
                                }
                        }
                        section=="Trousers"->{
                            database= FirebaseDatabase.getInstance().getReference("Products")
                            val newSectionRef = database.child("Trousers").push()
                            newSectionRef.setValue(product)
                                .addOnSuccessListener {
                                    Log.d(ContentValues.TAG, "Succes in Trousers")
                                }
                                .addOnFailureListener {
                                    Log.d(ContentValues.TAG, "Fail in Trousers")
                                }
                        }
                        section=="Hats"->{
                            database= FirebaseDatabase.getInstance().getReference("Products")
                            val newSectionRef = database.child("Hats").push()
                            newSectionRef.setValue(product)
                                .addOnSuccessListener {
                                    Log.d(ContentValues.TAG, "succes in Hats")
                                }
                                .addOnFailureListener {
                                    Log.d(ContentValues.TAG, "fail in Hats")
                                }
                        }
                        section=="Gadgets"->{
                            database= FirebaseDatabase.getInstance().getReference("Products")
                            val newSectionRef = database.child("Gadgets").push()
                            newSectionRef.setValue(product)
                                .addOnSuccessListener {
                                    Log.d(ContentValues.TAG, "succes in Gadgets")
                                }
                                .addOnFailureListener {
                                    Log.d(ContentValues.TAG, "fail in Gadgets")
                                }
                        }
                        section=="Shoes"->{
                            database= FirebaseDatabase.getInstance().getReference("Products")
                            val newSectionRef = database.child("Shoes").push()
                            newSectionRef.setValue(product)
                                .addOnSuccessListener {
                                    Log.d(ContentValues.TAG, "succes in shoes")
                                }
                                .addOnFailureListener {
                                    Log.d(ContentValues.TAG, "fail in shoes")
                                }
                        }
                    }



                //adding items on recyleview
                    itemArrayforseller.add(product)
                    recviewSeller.adapter= RecAdapterForSeller(requireActivity(),itemArrayforseller)

            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }



    }




    private fun getdatafromdatabase() {
        Log.d(ContentValues.TAG, "log3 in getdatafrbs")

        val arrayofsection= arrayOf(
            "Hats",
            "Shirts",
            "Trousers",
            "Shoes",
            "Gadgets"
        )
        val database=FirebaseDatabase.getInstance().getReference("Products")
        val typeIndicator = object : GenericTypeIndicator<HashMap<String, Any>>() {}
        arrayofsection.forEach {
            database.child(it)
                .addChildEventListener(object: ChildEventListener {
                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        Log.d(ContentValues.TAG, "logchecking")

                        if (!isAdded) {
                            return
                        }
                        val prduct = Product()
                        val data = snapshot.getValue(typeIndicator)
                        Log.d(ContentValues.TAG, "in onChildadded $data")
                        data?.forEach {
                            try {

                                when (it.key) {
                                    "imageId" -> prduct.imageId = it.value as String
                                    "shipping" -> prduct.shipping = it.value as String
                                    "price" -> prduct.price = it.value as Double
                                    "rating" -> prduct.rating = it.value as Double
                                    "description" -> prduct.description = it.value as String
                                }
                            } catch (e: Exception) {
                                when (it.key) {
                                    "imageId" -> prduct.imageId = it.value as String
                                    "shipping" -> prduct.shipping = it.value as String
                                    "price" -> prduct.price = (it.value as Long).toDouble()
                                    "rating" -> prduct.rating = (it.value as Long).toDouble()
                                    "description" -> prduct.description = it.value as String
                                }
                            }
                        }
                        itemArrayforseller.add(prduct)
                        Log.d(ContentValues.TAG, "${prduct}")
                        Log.d(ContentValues.TAG, "${itemArrayforseller}")
                        val adapter = recviewSeller.adapter as RecAdapterForSeller
                        adapter.notifyDataSetChanged()
                        recviewSeller.adapter= RecAdapterForSeller(requireActivity(),itemArrayforseller)
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



    private fun setmagazinename(view: View) {
        magazinename=view.findViewById(R.id.magName)
        sharedPref=requireActivity().getSharedPreferences("mypref", MODE_PRIVATE)
        val value=sharedPref.getString("key","magazine name")
        magazinename.text=value
    }

}
