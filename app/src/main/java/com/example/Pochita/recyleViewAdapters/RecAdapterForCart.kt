package com.example.Pochita.recyleViewAdapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.Pochita.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Pochita.Productclass.Product
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class RecAdapterForCart(private val context: Context, private val productList:ArrayList<Product>,):
    RecyclerView.Adapter<RecAdapterForCart.ViewHolder>() {


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val image=itemView.findViewById<ImageView>(R.id.image)
        val Price=itemView.findViewById<TextView>(R.id.price)
        val rating=itemView.findViewById<TextView>(R.id.rating)
        val description=itemView.findViewById<TextView>(R.id.description)
        val shipping=itemView.findViewById<TextView>(R.id.shipp)
        val Relative_row=itemView.findViewById<RelativeLayout>(R.id.Relative_row)

        fun setData(product: Product){
            Glide.with(itemView)
                .load(product.imageId)
                .into(image)
            Price.text=product.price.toString()
            rating.text=product.rating.toString()
            description.text=product.description
            shipping.text=product.shipping
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(productList[position])

        holder.Relative_row.setOnClickListener {
            showalertdialog(holder,position)

        }
    }
    override fun getItemCount(): Int {
        return productList.size
    }





    private fun showalertdialog(holder: ViewHolder,position: Int) {
        AlertDialog.Builder(context as Activity)
            .setTitle("Prdocut")
            .setMessage("you sure u want to buy that product?")
            .setPositiveButton("buy"){dialog,_->
                startBuying(holder,position)
                dialog.cancel()
            }
            .setNeutralButton("Remove"){_,_->
                removeitemfromdatabase(position)
                removeitem(position)

            }
            .setNegativeButton("Cancel"){dialog,_->
                dialog.cancel()
            }
            .show()
    }

    private fun startBuying(holder:ViewHolder,position: Int) {
        Toast.makeText(context,"your product is being reviewing",Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            val snackBar = Snackbar.make(
                holder.Relative_row, // The view to find a parent from
                "your product is on his way", // The text to show
                Snackbar.LENGTH_SHORT // The duration of the snackbar
            ).show()
        },60000)
        Handler(Looper.getMainLooper()).postDelayed({
            addProductInReviewed(position)
        },600000)
        addProductInShipped(position)
    }

    private fun removeitemfromdatabase(position: Int) {
        val database=FirebaseDatabase.getInstance().getReference("Carts")
        val CurrentUser= FirebaseAuth.getInstance().currentUser
        val query =database.child("User:${CurrentUser?.uid}")
            .orderByChild("imageId").equalTo(productList[position].imageId)
        query.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    Log.d("tag","this is ${childSnapshot}")
                    childSnapshot.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("tag","i am in onCancelled")
            }

        })
    }

    private fun removeitem(position: Int) {
        productList.removeAt(position)
        notifyItemRemoved(position)
    }
    private fun addProductInShipped(position: Int) {
        val database=FirebaseDatabase.getInstance().getReference("Shipped products")
        val currentUser=FirebaseAuth.getInstance().currentUser
        database.child("User:${currentUser?.uid}").push()
            .setValue(productList[position])
            .addOnSuccessListener {
                Log.d("mytag","succes in shipped")
            }
            .addOnFailureListener {
                Log.d("mytag","failed in shipped")
            }
    }
    fun addProductInReviewed(position: Int) {
        val database=FirebaseDatabase.getInstance().getReference("Reviewed Products")
        val currentUser=FirebaseAuth.getInstance().currentUser
        database.child("User:${currentUser?.uid}").push()
            .setValue(productList[position])
            .addOnSuccessListener {
                Log.d("mytag","succes in shipped")
            }
            .addOnFailureListener {
                Log.d("mytag","failed in shipped")
            }
    }
}