package com.example.Pochita.recyleViewAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.R
import com.example.Pochita.User.UserAddress


class RecAdapterForAddress(private val context: Context, private val addresList:ArrayList<UserAddress>,):
    RecyclerView.Adapter<RecAdapterForAddress.ViewHolder>() {


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val Name=itemView.findViewById<TextView>(R.id.name)
        val phone_Num=itemView.findViewById<TextView>(R.id.phone)
        val countrY=itemView.findViewById<TextView>(R.id.counrty)
        val state=itemView.findViewById<TextView>(R.id.state)
        val city=itemView.findViewById<TextView>(R.id.city)
        val zip_code=itemView.findViewById<TextView>(R.id.zip)

        fun setData(address: UserAddress){
            Name.text=address.fullname
            phone_Num.text=address.phonenumb
            countrY.text=address.country
            state.text=address.state
            city.text=address.city
            zip_code.text=address.zipcode
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.addres_view,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(addresList[position])

    }


    override fun getItemCount(): Int {
        return addresList.size
    }






}