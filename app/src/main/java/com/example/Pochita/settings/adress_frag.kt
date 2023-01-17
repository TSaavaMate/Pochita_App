package com.example.Pochita.settings

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.example.Pochita.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.User.UserAddress
import com.example.Pochita.recyleViewAdapters.RecAdapterForAddress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class adress_frag : Fragment(R.layout.fragment_adress_frag) {
    private lateinit var recViewAddress: RecyclerView
    private lateinit var itemArrayforAddress:ArrayList<UserAddress>
    private lateinit var button_adres:Button
    private lateinit var database:DatabaseReference
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_adres=view.findViewById(R.id.button_add_address)
        button_adres.setOnClickListener {
            showAlertDialogAndPutInfo()
        }


        itemArrayforAddress= arrayListOf()


        recViewAddress=view.findViewById(R.id.recyleView_for_Addres)
        recViewAddress.layoutManager= LinearLayoutManager(requireActivity())
        recViewAddress.setHasFixedSize(true)

        getdatafromdatabase()

        recViewAddress.adapter=RecAdapterForAddress(requireActivity(),itemArrayforAddress)
    }

    private fun getdatafromdatabase() {
        val database = FirebaseDatabase.getInstance().getReference("User_addres")
        val typeIndicator = object : GenericTypeIndicator<HashMap<String, Any>>() {}
        val currentUser=FirebaseAuth.getInstance().currentUser
        database.child("User:${currentUser?.uid}")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d(ContentValues.TAG, "logchecking")

                    if (!isAdded) {
                        return
                    }
                    val address = UserAddress()
                    val data = snapshot.getValue(typeIndicator)
                    Log.d(ContentValues.TAG, "in onChildadded $data")
                    data?.forEach {
                        when (it.key) {
                            "fullname" -> address.fullname = it.value as String
                            "phonenumb" -> address.phonenumb = it.value as String
                            "country" -> address.country = it.value as String
                            "state" -> address.state = it.value as String
                            "city" -> address.city = it.value as String
                            "zipcode" -> address.zipcode = it.value as String
                        }
                    }
                    itemArrayforAddress.add(address)
                    Log.d(ContentValues.TAG, "${address}")
                    Log.d(ContentValues.TAG, "${itemArrayforAddress}")
                    val adapter = recViewAddress.adapter as RecAdapterForAddress
                    adapter.notifyDataSetChanged()
                    recViewAddress.adapter = RecAdapterForAddress(requireActivity(), itemArrayforAddress)
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

    private fun showAlertDialogAndPutInfo() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("adding address")
        builder.setMessage("enter your data")
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.alertdialog_for_address, null)
        builder.setView(dialogView)
        val fullname=dialogView.findViewById<EditText>(R.id.fullname)
        val phonenumb=dialogView.findViewById<EditText>(R.id.phonenum)
        val counrty=dialogView.findViewById<EditText>(R.id.counrty_in_address)
        val state=dialogView.findViewById<EditText>(R.id.state_in_adress)
        val city=dialogView.findViewById<EditText>(R.id.city_in_adress)
        val zip_postal=dialogView.findViewById<EditText>(R.id.zipcode)
        builder.setPositiveButton("Save"){_,_->
            var validation=true
            when{
                TextUtils.isEmpty(fullname.text.toString())->{validation=false}
                TextUtils.isEmpty(phonenumb.text.toString())->{validation=false}
                TextUtils.isEmpty(counrty.text.toString())->{validation=false}
                TextUtils.isEmpty(state.text.toString())->{validation=false}
                TextUtils.isEmpty(city.text.toString())->{validation=false}
                TextUtils.isEmpty(zip_postal.text.toString())->{validation=false}
            }
            if(validation) {
                val user_Address = UserAddress(
                    fullname.text.toString(),
                    phonenumb.text.toString(),
                    counrty.text.toString(),
                    state.text.toString(),
                    city.text.toString(),
                    zip_postal.text.toString(),
                )
                database = FirebaseDatabase.getInstance().getReference("User_addres")
                val Cureentuser = FirebaseAuth.getInstance().currentUser
                val newRef = database.child("User:${Cureentuser?.uid}").push()
                newRef.setValue(user_Address)
            }else{
                Toast.makeText(requireActivity(),"wrong input try again",Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

}