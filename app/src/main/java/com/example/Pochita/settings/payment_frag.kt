package com.example.Pochita.settings

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.Pochita.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.User.UserCard
import com.example.Pochita.recyleViewAdapters.RecAdapterForPayment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class payment_frag : Fragment(R.layout.fragment_payment_frag) {
    private lateinit var recViewForPayment:RecyclerView
    private lateinit var fab:FloatingActionButton
    private lateinit var database: DatabaseReference
    private lateinit var itemArrayOfCard: ArrayList<UserCard>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recViewForPayment=view.findViewById(R.id.recViewforpayment)
        fab=view.findViewById(R.id.floatinActionButton)
        fab.setOnClickListener{
            showAlertDialogAndPutInfo()
        }


        itemArrayOfCard= arrayListOf()

        recViewForPayment.layoutManager= LinearLayoutManager(requireActivity())
        recViewForPayment.setHasFixedSize(true)

        getDataFromDatabase()

        recViewForPayment.adapter= RecAdapterForPayment(requireActivity(),itemArrayOfCard)


    }










    private fun getDataFromDatabase() {
        val database = FirebaseDatabase.getInstance().getReference("User_Cards")
        val typeIndicator = object : GenericTypeIndicator<HashMap<String, Any>>() {}
        val currentUser=FirebaseAuth.getInstance().currentUser
        database.child("User:${currentUser?.uid}")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d(ContentValues.TAG, "logchecking")

                    if (!isAdded) {
                        return
                    }
                    val card = UserCard()
                    val data = snapshot.getValue(typeIndicator)
                    Log.d(ContentValues.TAG, "in onChildadded $data")
                    data?.forEach {
                        try {
                            when (it.key) {
                                "name" -> card.name = it.value as String
                                "number" -> card.number = it.value as Long
                                "date" -> card.date = it.value as String
                                "cvv" -> card.CVV =(it.value as Long).toInt()
                            }
                        }catch (e:Exception){
                            Toast.makeText(requireActivity(),"input card data correctly",Toast.LENGTH_SHORT).show()
                            Log.d("tag","logging in try catch")

                        }
                    }
                    itemArrayOfCard.add(card)
                    Log.d(ContentValues.TAG, "${card}")
                    Log.d(ContentValues.TAG, "${itemArrayOfCard}")
                    val adapter = recViewForPayment.adapter as RecAdapterForPayment
                    adapter.notifyDataSetChanged()
                    recViewForPayment.adapter = RecAdapterForPayment(requireActivity(), itemArrayOfCard)
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
        builder.setTitle("adding card")
        builder.setMessage("enter your card's data")
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.alertdialog_for_payment, null)
        builder.setView(dialogView)
        val name=dialogView.findViewById<EditText>(R.id.fullName)
        val cardNum=dialogView.findViewById<EditText>(R.id.cardNumber)
        val dateOnCard=dialogView.findViewById<EditText>(R.id.DateofCard)
        val CVV=dialogView.findViewById<EditText>(R.id.CCvv)
        builder.setPositiveButton("add"){_,_->
            var isValidationSuccessful=true
            when{
                TextUtils.isEmpty(name.text.toString().trim())->{name.error="input name "
                    isValidationSuccessful = false
                }
                TextUtils.isEmpty(cardNum.text.toString().trim())->{cardNum.error="input card number "
                    isValidationSuccessful = false
                }
                TextUtils.isEmpty(dateOnCard.text.toString().trim())->{dateOnCard.error="input date "
                    isValidationSuccessful = false
                }
                TextUtils.isEmpty(CVV.text.toString().trim())->{CVV.error="input CVV"
                    isValidationSuccessful = false
                }
                name.text.toString()==name.text.toString().trim()->{name.error="you must input name and surname form card"
                    isValidationSuccessful = false
                }
                cardNum.text.toString().length!=16->{cardNum.error="card number must be 16 digits"
                    isValidationSuccessful = false
                }
                CVV.text.toString().length!=3->{CVV.error="CVV must be 3 digits"
                    isValidationSuccessful = false
                }
            }

            if(isValidationSuccessful) {
                val card=UserCard(
                    name.text.toString(),
                    cardNum.text.toString().toLong(),
                    dateOnCard.text.toString(),
                    CVV.text.toString().toInt()
                )
                database= FirebaseDatabase.getInstance().getReference("User_Cards")
                val Cureentuser= FirebaseAuth.getInstance().currentUser
                val newRef = database.child("User:${Cureentuser?.uid}").push()
                newRef.setValue(card)
            }
            else {
                Toast.makeText(requireActivity(),"card info was wrong,try again",Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.setCancelable(false)
        builder.setOnDismissListener(null)
        builder.show()
    }
}