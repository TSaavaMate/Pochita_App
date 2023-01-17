package com.example.Pochita.recyleViewAdapters

import android.content.ContentValues
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Pochita.Productclass.Product
import com.example.Pochita.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class RecAdapter(private val context:Context, private val productList:ArrayList<Product>,):
    RecyclerView.Adapter<RecAdapter.ViewHolder>() {

    private lateinit var database: DatabaseReference

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

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
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(productList[position])

        holder.Relative_row.setOnClickListener {
            showDialog(position,holder)
        }
        holder.Relative_row.setOnLongClickListener {
            addtowishlist(position)
            Toast.makeText(context,"item added to wish list",Toast.LENGTH_SHORT).show()
             true
        }
    }


    override fun getItemCount(): Int {
        return productList.size
    }

    private fun addtowishlist(position: Int) {
        database=FirebaseDatabase.getInstance().getReference("Wish_list")
        val Curentuser=FirebaseAuth.getInstance().currentUser
        val newRef=database.child("User:${Curentuser?.uid}").push()
        newRef.setValue(productList[position])
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "succes in wishlist")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "failed in wishlist")
            }
    }
    private fun showDialog(position: Int,holder:ViewHolder) {
        val bottomSheetDialog = BottomSheetDialog(context,R.style.AppBottomSheetDialogTheme)
        val itemView=LayoutInflater.from(context).inflate(R.layout.bottomdialog_recyleview,null)
        val buttoncart:Button=itemView.findViewById(R.id.btncart)
        val buttonbuy:Button=itemView.findViewById(R.id.btnbuy)
        val price:TextView=itemView.findViewById(R.id.forprice)
        val shipping:TextView=itemView.findViewById(R.id.forshipping)
        val rating:TextView=itemView.findViewById(R.id.forrating)
        val image:ImageView=itemView.findViewById(R.id.item)
        Glide.with(itemView)
            .load(productList[position].imageId)
            .circleCrop()
            .into(image)
        price.text=productList[position].price.toString()
        shipping.text=productList[position].shipping
        rating.text=productList[position].rating.toString()
        buttoncart.setOnClickListener {
            database=FirebaseDatabase.getInstance().getReference("Carts")
            val CurrentUser= FirebaseAuth.getInstance().currentUser
            val newCartRef = database.child("User:${CurrentUser?.uid}").push()
            val cartId = newCartRef.key
            val cart = productList[position]
            newCartRef.setValue(cart).addOnSuccessListener {
                Toast.makeText(context,"Added to Cart",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"Failed to add to Cart",Toast.LENGTH_SHORT).show()
            }
            bottomSheetDialog.dismiss()
        }
        buttonbuy.setOnClickListener {
            Toast.makeText(context,"your product is being reviewing",Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
            Handler(Looper.getMainLooper()).postDelayed({
                val snackBar = make(
                    holder.Relative_row, // The view to find a parent from
                    "your product is on his way", // The text to show
                    LENGTH_SHORT // The duration of the snackbar
                ).show()
            },6000)
            Handler(Looper.getMainLooper()).postDelayed({
                addProductInReviewed(position)
            },600)
            addProductInShipped(position)
        }
        bottomSheetDialog.setContentView(itemView)
        bottomSheetDialog.show()
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
        val database=FirebaseDatabase.getInstance().getReference("Reviewed_Products")
        val currentUser=FirebaseAuth.getInstance().currentUser
        database.child("User:${currentUser?.uid}").push()
            .setValue(productList[position])
            .addOnSuccessListener {
                Log.d("mytag","succes in review")
            }
            .addOnFailureListener {
                Log.d("mytag","failed in review")
            }
    }
}