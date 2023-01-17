package com.example.Pochita

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow


class helpAction : Fragment(R.layout.helpaction) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.feedback_send_oustide_auth).setOnClickListener {
            Toast.makeText(activity,"feedback is sent",Toast.LENGTH_LONG).show()
            val fragmentManager = parentFragmentManager
            fragmentManager.commitNow {
                replace(R.id.nav_host_fragment, FirstMainPage())
            }
        }
    }//returning on mainpage

}