package com.example.Pochita.settings

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.Pochita.R
import com.example.Pochita.feedback.Feedback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class Helpcenterfrag : Fragment(R.layout.fragment_helpcenter_frag) {
   private lateinit var feedback:TextView
   private lateinit var btn_send:Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedback=view.findViewById(R.id.feedback)
        btn_send=view.findViewById(R.id.feedback_send)
        btn_send.setOnClickListener {
            if (feedback.text.toString().length==0) {feedback.error="type text"; return@setOnClickListener}
            val fdbk= Feedback(feedback.text.toString())
            val database=FirebaseDatabase.getInstance().getReference("feedback")
            val currentUser=FirebaseAuth.getInstance().currentUser
            database.child("From User:${currentUser?.uid}").push()
                .setValue(fdbk)
                .addOnSuccessListener {
                    Toast.makeText(requireActivity(),"feedback is sent",Toast.LENGTH_SHORT).show()
                }
        }
    }
}