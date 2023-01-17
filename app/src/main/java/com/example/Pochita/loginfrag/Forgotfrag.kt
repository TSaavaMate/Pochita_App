package com.example.Pochita.loginfrag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.Pochita.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class Forgotfrag : Fragment(R.layout.fragment_forgotfrag) {
    private lateinit var button_forgot: Button
    private lateinit var email_forgot:EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_forgot=view.findViewById(R.id.button_forgot)
        email_forgot=view.findViewById(R.id.email_forgot)
        button_forgot.setOnClickListener {
            val email=email_forgot.text.toString()
            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Snackbar.make(view,"check your email",Snackbar.LENGTH_SHORT)
                        .setAction("action",null)
                        .show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireActivity(),"something went wrong,try again",Toast.LENGTH_LONG)
                        .show()
                }

        }

    }
}