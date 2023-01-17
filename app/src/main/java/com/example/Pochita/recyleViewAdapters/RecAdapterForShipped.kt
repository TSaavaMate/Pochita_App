package com.example.Pochita.recyleViewAdapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Pochita.Productclass.Product
import com.example.Pochita.R
import com.google.firebase.database.DatabaseReference


class RecAdapterForShipped(private val context: Context, private val productList:ArrayList<Product>,):
    RecyclerView.Adapter<RecAdapterForShipped.ViewHolder>() {


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
            showalertdialog()
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    private fun showalertdialog() {
        AlertDialog.Builder(context as Activity)
            .setTitle("Prdocut")
            .setMessage("item is being shipped")
            .setPositiveButton("wait"){dialog,_->
                dialog.cancel()
            }
            .setNeutralButton("cancel order"){dialog,_->
                dialog.cancel()
                AlertDialog.Builder(context)
                    .setTitle("canceling order")
                    .setMessage("if you cancel order your money won't be refunded")
                    .setPositiveButton("okay"){dialog2,_->
                        dialog2.cancel()
                    }
                    .setNegativeButton("cancel"){dialog2,_,->
                        dialog2.cancel()
                    }
                    .show()
            }
            .setNegativeButton("Cancel"){dialog,_->
                dialog.cancel()
            }
            .show()
    }

}