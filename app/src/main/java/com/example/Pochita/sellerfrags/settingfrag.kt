package com.example.Pochita.sellerfrags

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.Pochita.FirstMainPage
import com.example.Pochita.R
import com.example.Pochita.seller.sellerfrag
import com.example.Pochita.settings.Helpcenterfrag
import com.google.firebase.auth.FirebaseAuth


class settingfrag : Fragment(R.layout.fragment_settingfrag) {
    private lateinit var back_btn:ImageView
    private lateinit var help_center:RelativeLayout
    private lateinit var passRecover:RelativeLayout
    private lateinit var rated:RelativeLayout
    private lateinit var unsuccesful:RelativeLayout
    private lateinit var discarded:RelativeLayout
    private lateinit var address:RelativeLayout
    private lateinit var disputes:RelativeLayout
    private lateinit var payment:RelativeLayout
    private lateinit var ongoing:RelativeLayout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ongoing=view.findViewById(R.id.ongoing_relative)
        ongoing.setOnClickListener {
            replaceWithFragment(onGoingItems())
        }


        payment=view.findViewById(R.id.seller_payment_relative)
        payment.setOnClickListener {
            showpaymentdialog()
        }

        disputes=view.findViewById(R.id.disputes_relative)
        disputes.setOnClickListener {
            Toast.makeText(requireActivity(),"no disputes written   yet",Toast.LENGTH_SHORT).show()

        }

        address=view.findViewById(R.id.seller_adress_relative)
        address.setOnClickListener {
            showaddressalertdialog()
        }

        unsuccesful=view.findViewById(R.id.sel_unpaid_items_relative)
        unsuccesful.setOnClickListener {
            Toast.makeText(requireActivity(),"no unsuccesful products  yet",Toast.LENGTH_SHORT).show()

        }

        discarded=view.findViewById(R.id.sel_discarded_items_relative)
        discarded.setOnClickListener {
            Toast.makeText(requireActivity(),"no discarded products  yet",Toast.LENGTH_SHORT).show()

        }

        rated=view.findViewById(R.id.rated_prod_relative)
        rated.setOnClickListener {
            Toast.makeText(requireActivity(),"no products rated yet",Toast.LENGTH_SHORT).show()
        }

        passRecover=view.findViewById(R.id.help_pass_relative)
        passRecover.setOnClickListener {
            showalertdialog()
        }

        help_center=view.findViewById(R.id.sell_help_center_relative)
        help_center.setOnClickListener {
            replaceWithFragment(Helpcenterfrag())
        }


        back_btn=view.findViewById(R.id.sel_back)
        back_btn.setOnClickListener{
            replaceWithFragment(sellerfrag())
        }

        val buttonS: Button =view.findViewById(R.id.button_signout)
        buttonS.setOnClickListener {
            removefromsharedpref()
        }
    }

    private fun showpaymentdialog() {
        val builder = android.app.AlertDialog.Builder(requireActivity())
        builder.setTitle("adding card")
        builder.setMessage("enter your card's data")
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.alertdialog_for_payment, null)
        builder.setView(dialogView)
        builder.setPositiveButton("Save"){_,_->
            Log.d("tagging","saved")
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun showaddressalertdialog() {
        val addressdialog=AlertDialog.Builder(requireActivity())
        val layout=layoutInflater.inflate(R.layout.alertdialog_for_address,null)
        layout.findViewById<EditText?>(R.id.zipcode).visibility=View.GONE
        addressdialog.setView(layout)
            .setTitle("add address info")
            .setMessage("note")
            .setPositiveButton("confirm"){_,_->
                Toast.makeText(requireActivity(),"note added",Toast.LENGTH_SHORT).show()
            }
            .show()

    }

    private fun showalertdialog() {
        val alert=AlertDialog.Builder(requireActivity())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.alert_for_change_pass, null)
        alert.setView(dialogView)
            .setTitle("changing password")
            .setMessage("type new password")
            .setPositiveButton("confirm"){_,_->
                val password=dialogView.findViewById<EditText>(R.id.current)
                val user=FirebaseAuth.getInstance().currentUser
                user?.updatePassword(password.text.toString())
                    ?.addOnSuccessListener {
                        Toast.makeText(requireActivity(),"password changed succesfuly",Toast.LENGTH_SHORT).show()
                    }
                    ?.addOnFailureListener {
                        Log.d("taaging","failed changing password")
                    }
            }
            .show()
    }

    private fun removefromsharedpref() {
        val sharedPref=requireActivity().getSharedPreferences("Login_pref", Context.MODE_PRIVATE)
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

    private fun replaceWithFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.setReorderingAllowed(true)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}