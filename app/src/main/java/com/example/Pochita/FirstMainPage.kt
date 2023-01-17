package com.example.Pochita

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.*
import com.example.Pochita.Mainpage.MainFrag
import com.example.Pochita.loginfrag.Forgotfrag
import com.example.Pochita.loginfrag.Joinfrag
import com.example.Pochita.seller.sellerfrag
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*


class FirstMainPage : Fragment(R.layout.fragment_firstmainpage) {
    private lateinit var button_action: Button
    private lateinit var button_signin: Button
    private lateinit var button_join: Button
    private lateinit var button_google: Button
    private lateinit var textView: TextView
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth:FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkuser()



        button_action=view.findViewById(R.id.button_action)
        button_action.setOnClickListener {
            val fragmentManager = parentFragmentManager
            fragmentManager.commitNow {
                replace(R.id.nav_host_fragment, helpAction())
            }
        }//helpcenter


        button_join=view.findViewById(R.id.joinbutton)
        button_signin=view.findViewById(R.id.signinbutton)
        button_google=view.findViewById(R.id.googlebutton)
        textView=view.findViewById(R.id.edittext)


        button_signin.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireActivity(),R.style.AppBottomSheetDialogTheme)
            val signinView = layoutInflater.inflate(R.layout.fragment_logfrag,null)
            val textView:TextView= signinView.findViewById(R.id.textforgotpassword1)
            val email_log:EditText=signinView.findViewById(R.id.emaillog)
            val password_log:EditText=signinView.findViewById(R.id.passwordlog)
            val button_sign_in:Button=signinView.findViewById(R.id.buttonsignin)
            val checkbox_remember:CheckBox=signinView.findViewById(R.id.checkBox_remember)


            button_sign_in.setOnClickListener {
                val email=email_log.text.toString()
                val password=password_log.text.toString()
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener {

                        database= FirebaseDatabase.getInstance().getReference("Users")
                        val CurrentUser= FirebaseAuth.getInstance().currentUser
                        database.child("person:${CurrentUser?.uid}")

                            //listens to persons data from database
                            .addListenerForSingleValueEvent(object: ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val userType = snapshot.child("usertype").getValue(String::class.java)
                                    if (checkbox_remember.isChecked){
                                        checkbox_remember.setBackgroundColor(Color.RED)
                                        val sharedPref=requireActivity().getSharedPreferences("Login_pref",Context.MODE_PRIVATE)
                                        val editor=sharedPref.edit()
                                        editor.putString("email",email)
                                        editor.putString("password",password)
                                        editor.putString("usertype",userType)
                                        editor.apply()
                                    }//saves log and pass in sharedpref


                                    when (userType) {
                                        "seller" -> {
                                            val fragmentManager = parentFragmentManager
                                            fragmentManager.commitNow {
                                                replace(R.id.nav_host_fragment, sellerfrag())
                                            }
                                            bottomSheetDialog.dismiss()
                                        }
                                        "buyer" -> {
                                            val fragmentManager = parentFragmentManager
                                            fragmentManager.commitNow {
                                                replace(R.id.nav_host_fragment, MainFrag())
                                            }
                                            bottomSheetDialog.dismiss()
                                        }
                                        else -> {
                                            Log.d(ContentValues.TAG, "this is log in onDataChange in 3rd whencondition ")

                                        }
                                    } //checks who user is
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Log.d("tag","log in oncanclled")
                                }
                            })
                        bottomSheetDialog.dismiss()
                    }//checks usertype
                    .addOnFailureListener {
                        Toast.makeText(requireActivity(),"Unseccesful",Toast.LENGTH_SHORT).show()
                    }//toast

            }
            textView.setOnClickListener {
                val fragmentManager=activity?.supportFragmentManager
                fragmentManager?.commit {
                    setReorderingAllowed(true)
                    replace<Forgotfrag>(R.id.nav_host_fragment)
                }
                bottomSheetDialog.dismiss()
            }//navigates to forgortfrag

            bottomSheetDialog.setContentView(signinView)
            bottomSheetDialog.show()
        }
        button_join.setOnClickListener {
            replaceWithFragment(Joinfrag())
        }//navigates to Joinfrag


        //authenticating with google



        button_google.setOnClickListener {
            auth = FirebaseAuth.getInstance()

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(requireActivity() , gso)
            signInGoogle()
        }




    }
    private fun signInGoogle(){
        // Initiate the Google sign-in process
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
        Log.d("TAG", "onActivityResult1")
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        // Handle the result of the sign-in process
            result ->
        Log.d("TAG", "onActivityResult2")
        if (result.resultCode == Activity.RESULT_OK){
            Log.d("TAG", "onActivityResult3")
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }else{
            Log.d("TAG", "onActivityResult4")
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        // Check if the sign-in task was successful
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
                Log.d("TAG", "onHandle")
            }
        }else{
            Log.d("TAG", "unsuccesfulHandle")
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        // Create a new credential using the GoogleSignInAccount's idToken
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                val fragmentManager=activity?.supportFragmentManager
                fragmentManager?.commit {
                    setReorderingAllowed(true)
                    replace<MainFrag>(R.id.nav_host_fragment)
                }
            }
            .addOnFailureListener {
                Log.d("TAG", "unsuccesful")

            }
    }







    private fun checkuser() {
        val sharedpref=requireActivity().getSharedPreferences("Login_pref",Context.MODE_PRIVATE)
        val username=sharedpref.getString("email","")
        val password=sharedpref.getString("password","")
        val usertype=sharedpref.getString("usertype","")
        if (username!=null && password!=null) {
            when (usertype) {
                "seller" -> {
                    val fragmentManager=activity?.supportFragmentManager
                    fragmentManager?.commit {
                        setReorderingAllowed(true)
                        replace<sellerfrag>(R.id.nav_host_fragment)
                    }
                }
                "buyer" -> {
                    val fragmentManager=activity?.supportFragmentManager
                    fragmentManager?.commit {
                        setReorderingAllowed(true)
                        replace<MainFrag>(R.id.nav_host_fragment)
                    }
                }
                else->{
                    Log.d("tag","log in checkuser")
                }
            }
        }
    }
    //Replace with transaction
    private fun replaceWithFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.setReorderingAllowed(true)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }




}






