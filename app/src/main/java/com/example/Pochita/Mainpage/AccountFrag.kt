package com.example.Pochita.Mainpage

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.bumptech.glide.Glide
import com.example.Pochita.FirstMainPage
import com.example.Pochita.R
import com.example.Pochita.homeFrags.ShirtFrag
import com.example.Pochita.settings.*
import com.google.firebase.auth.FirebaseAuth


class AccountFrag : Fragment(R.layout.fragment_account) {
    private lateinit var wishList:RelativeLayout
    private lateinit var shippeditems:RelativeLayout
    private lateinit var helpCenter:RelativeLayout
    private lateinit var reviewedProductFag:RelativeLayout
    private lateinit var address:RelativeLayout
    private lateinit var coupon_Center:RelativeLayout
    private lateinit var coupon:RelativeLayout
    private lateinit var discarded_items:RelativeLayout
    private lateinit var unpaid_items:RelativeLayout
    private lateinit var payment_meth:RelativeLayout
    private lateinit var imageView: ImageView
    private lateinit var currentname:TextView
    private lateinit var buttonsignout:Button
    private lateinit var back:ImageView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //when user is authenticated with google
        imageView=view.findViewById(R.id.imageonsettings)
        currentname=view.findViewById(R.id.currentusername)
        val CurrentUser=FirebaseAuth.getInstance().currentUser
        if (CurrentUser?.photoUrl!=null){
            Glide.with(this).load(CurrentUser.photoUrl).circleCrop().into(imageView)
            currentname.text=CurrentUser.displayName
        }else{
            currentname.visibility=View.GONE
        }
        


        //wish list fragment
        wishList=view.findViewById(R.id.wish_list_relative)
        wishList.setOnClickListener {
            replaceWithFragment(wishlist_frag())
        }


        //address fragment
        address=view.findViewById(R.id.adress_relative)
        address.setOnClickListener {
            replaceWithFragment(adress_frag())
        }

        //shippeditems fragment
        shippeditems=view.findViewById(R.id.shipped_relative)
        shippeditems.setOnClickListener {
            replaceWithFragment(shippeditems_frag())
        }


        //helpcenter fragment
        helpCenter=view.findViewById(R.id.help_center_relative)
        helpCenter.setOnClickListener {
            replaceWithFragment(Helpcenterfrag())
        }


        //reviewed fragment
        reviewedProductFag=view.findViewById(R.id.reviewed_prod_relative)
        reviewedProductFag.setOnClickListener {
            replaceWithFragment(reviewedprod_frag())
        }

        //coupon Center
        coupon_Center=view.findViewById(R.id.coupons_relative)
        coupon_Center.setOnClickListener {
            Toast.makeText(requireActivity(),"buy some product's first",Toast.LENGTH_SHORT).show()
        }



        //actual coupons
        coupon=view.findViewById(R.id.coupon_relative)
        coupon.setOnClickListener {
            AlertDialog.Builder(requireActivity())
                .setTitle("you don't have coupons yet")
                .setMessage("buy some products to get coupons")
                .setPositiveButton("okay"){_,_-> Log.d("tag","no coupons")}
                .show()
        }



        //discarded_items
        discarded_items=view.findViewById(R.id.discarded_items_relative)
        discarded_items.setOnClickListener {
            Toast.makeText(requireActivity(),"there is no items discarded yet",Toast.LENGTH_SHORT).show()
        }

        //unpaid items
        unpaid_items=view.findViewById(R.id.unpaid_items_relative)
        unpaid_items.setOnClickListener {
            Toast.makeText(requireActivity(),"there is no items which has been canceled yet",Toast.LENGTH_SHORT).show()
        }

        //payment method
        payment_meth=view.findViewById(R.id.payment_relative)
        payment_meth.setOnClickListener {
            replaceWithFragment(payment_frag())
        }



        //signout
        buttonsignout=view.findViewById(R.id.buttonsignout)
        buttonsignout.setOnClickListener {
            val sharedPref=requireActivity().getSharedPreferences("Login_pref",Context.MODE_PRIVATE)
            val editor=sharedPref.edit()
            editor.remove("email")
            editor.remove("password")
            editor.remove("usertype")
            editor.apply()
            FirebaseAuth.getInstance().signOut()
            val fragmentManager=activity?.supportFragmentManager
            fragmentManager?.commit {
                setReorderingAllowed(true)
                replace<FirstMainPage>(R.id.nav_host_fragment)
            }
        }

        //back
        back=view.findViewById(R.id.back)
        back.setOnClickListener {
            replaceWithFragment(MainFrag())
        }

    }
    //Replace with transaction
    private fun replaceWithFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.setReorderingAllowed(true)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

}