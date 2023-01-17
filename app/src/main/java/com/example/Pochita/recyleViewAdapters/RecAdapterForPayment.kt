package com.example.Pochita.recyleViewAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.Pochita.R
import com.example.Pochita.User.UserCard


class RecAdapterForPayment(private val context: Context, private val cardList:ArrayList<UserCard>,):
    RecyclerView.Adapter<RecAdapterForPayment.ViewHolder>() {


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val Name=itemView.findViewById<TextView>(R.id.Nameoncard)
        val number=itemView.findViewById<TextView>(R.id.Cardnumber)
        val date=itemView.findViewById<TextView>(R.id.dateOfCard)
        val CVV=itemView.findViewById<TextView>(R.id.Cvv)

        fun setData(card: UserCard){
            val card_Num=card.number.toString()
            val card_Num_Format="*".repeat(card_Num.length-4)+card_Num.substring(card_Num.length-4)
            Name.text=card.name
            number.text=card_Num_Format
            date.text=card.date
            CVV.text=card.CVV.toString()
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.payment_card_view,parent,false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setData(cardList[position])


    }


    override fun getItemCount(): Int {
        return cardList.size
    }


}