package com.example.Pochita.loginfrag

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import com.example.Pochita.FirstMainPage
import com.example.Pochita.R
import com.example.Pochita.User.User
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class Joinfrag : Fragment(R.layout.fragment_joinfrag) {
    private lateinit var Name:EditText
    private lateinit var surName:EditText
    private lateinit var phone:EditText
    private lateinit var date:EditText
    private lateinit var email:EditText
    private lateinit var recemail:EditText
    private lateinit var password1:EditText
    private lateinit var password2:EditText
    private lateinit var terms:TextView
    private lateinit var checkBox: CheckBox
    private lateinit var buttonJoin: Button
    private lateinit var textInputrecemail: TextInputLayout
    private lateinit var textInputdate: TextInputLayout
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //recovery email listener and icon

        recemail=view.findViewById(R.id.Recoveryemail)
        textInputrecemail=view.findViewById(R.id.textInputLayout3)
        textInputrecemail.isEndIconVisible = true
        textInputrecemail.setEndIconOnClickListener {
            val message="this email will be used when you will need to recover account,for double safety"
            Toast.makeText(requireActivity(),message,Toast.LENGTH_LONG).show()
        }


        //autocompletetextview for countries adapting

        val COUNTRIES = arrayOf(
            "Belgium", "France", "Italy", "Germany", "Spain", "Afganistan","Georgia",
            "Alandeilande","Albanië","Algerië","Amerikaanse","Andorra","Angola","Anguilla",
            "Antarktika","Antigua","Argentinië","Armenië","Aruba","Australië","Azerbeidjan",
            "Bahamas","Bahrein","Bangladesj","Barbados","Belarus","België","Belize",
            "Benin","Bermuda","Bhoetan","Bolivië","Bosnië","Botswana","Bouvet-eiland",
            "Brasilië","Brits-Indiese","Britse","Broenei","Bulgarye","Burkina","Burundi",
            "Chili","Colombië","Comore","Cookeilande","Costa","Curaçao","Demokratiese",
            "Denemarke","Djiboeti","Dominica","Dominikaanse","Duitsland","Ecuador","Egipte",
            "Eiland","Ekwatoriaal-Guinee","El","Eritrea","Estland","Eswatini","Ethiopië",
            "Falklandeilande","Faroëreilande","Fidji","Filippyne","Finland","Frankryk","Frans-Guyana",
            "Frans-Polinesië","Franse","Gaboen","Gambië","Georgië","Ghana","Gibraltar",
            "Grenada","Griekeland","Groenland","Guadeloupe","Guam","Guatemala","Guernsey",
            "Guinee","Guinee-Bissau","Guyana","Haïti","Heardeiland","Honduras","Hongarye",
            "Hongkong","Ierland","Indië","Indonesië","Irak","Iran","Israel",
            "Italië","Ivoorkus","Jamaika","Japan","Jemen","Jersey","Jordanië",
            "Kaaimanseilande","Kaap","Kambodja","Kameroen","Kanada","Karibiese","Katar",
            "Kazakstan","Kenia","Kerseiland","Kirgistan","Kiribati","Klein","Koeweit",
            "Kokoseilande","Kongo","Kroasië","Kuba","Laos","Lesotho","Letland",
            "Libanon","Liberië","Libië","Liechtenstein","Litaue","Luxemburg","Macau",
            "Madagaskar",

            )
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireActivity(),
            android.R.layout.simple_dropdown_item_1line, COUNTRIES
        )
        val userCountry = view.findViewById(R.id.autotextforcountry) as AutoCompleteTextView
        userCountry.setAdapter(adapter)

        //autocompletetextview for type adapting

        val choices = arrayOf(
            "seller", "buyer"
        )
        val adapter2: ArrayAdapter<String> = ArrayAdapter<String>(requireActivity(),
            android.R.layout.simple_dropdown_item_1line, choices
        )
        val userType = view.findViewById(R.id.autotextfortype) as AutoCompleteTextView
        userType.setAdapter(adapter2)

        //bottomsheetdialog for terms and agrements

        terms=view.findViewById(R.id.termsandagrements)
        terms.setOnClickListener {
            val bottomSheetDialog = activity?.let { it1 -> BottomSheetDialog(it1) }
            bottomSheetDialog?.setContentView(R.layout.terms)
            bottomSheetDialog?.show()
        }

        //datepickerdialog on date editext

        date=view.findViewById(R.id.date)
        textInputdate=view.findViewById(R.id.textInputLayoutfordate)
        textInputdate.isEndIconVisible = true
        textInputdate.setEndIconOnClickListener {
            val c= Calendar.getInstance()
            val year=c.get(Calendar.YEAR)
            val month=c.get(Calendar.MONTH)
            val day=c.get(Calendar.DAY_OF_MONTH)
            val dpd= DatePickerDialog(requireActivity(), { view: DatePicker, mYear, mMonth, mDay ->
                val text = getString(R.string.date_format, mDay, mMonth, mYear)
                date.setText(text)
            },year,month,day)
            dpd.show()

        }



        //findingeditexts
        Name=view.findViewById(R.id.Name)
        surName=view.findViewById(R.id.Surname)
        phone=view.findViewById(R.id.phone)
        email=view.findViewById(R.id.emailwhenjoin)
        password1=view.findViewById(R.id.passwordwhenjoin)
        password2=view.findViewById(R.id.secpasswordwhenjoin)
        checkBox=view.findViewById(R.id.checkBox)
        buttonJoin=view.findViewById(R.id.buttonJoin)


        buttonJoin.setOnClickListener {

            //cheks if some edittext field is empty

            when{
                TextUtils.isEmpty(Name.text.toString())-> { Name.error = "input Name"
                return@setOnClickListener}

                TextUtils.isEmpty(surName.text.toString())-> { surName.error = "input surName"
                    return@setOnClickListener}

                TextUtils.isEmpty(phone.text.toString())-> { phone.error = "input phonenumber"
                    return@setOnClickListener}

                TextUtils.isEmpty(date.text.toString())-> { date.error = "input date"
                    return@setOnClickListener}

                TextUtils.isEmpty(email.text.toString())-> { email.error = "input email"
                    return@setOnClickListener}

                TextUtils.isEmpty(recemail.text.toString())-> { recemail.error = "input second email"
                    return@setOnClickListener}

                TextUtils.isEmpty(password1.text.toString())-> { password1.error = "input password"
                    return@setOnClickListener}

                TextUtils.isEmpty(password2.text.toString())-> { password2.error = "input password again"
                    return@setOnClickListener}

                password1.text.toString()!=password2.text.toString()->{password2.error="password's must be same"
                    return@setOnClickListener}
            }


            //creates  user data

            val person= User(Name.text.toString(),
                surName.text.toString(),
                phone.text.toString(),
                date.text.toString(),
                userCountry.text.toString(),
                userType.text.toString(),
                email.text.toString(),
                recemail.text.toString(),
            )



            // creates   user  on FirebaseAuth

            val email=email.text.toString()
            val password=password1.text.toString()
            firebaseAuth= FirebaseAuth.getInstance()
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener {


                    database= FirebaseDatabase.getInstance().getReference("Users")
                    val CurrentUser= FirebaseAuth.getInstance().currentUser
                    val newUserRef = database.child("person:${CurrentUser?.uid}")
                    newUserRef.setValue(person)
                    Toast.makeText(requireActivity(),"you are registered",Toast.LENGTH_SHORT).show()

                    //returns on MainPage
                    val fragmentManager = parentFragmentManager
                    fragmentManager.commitNow {
                        replace(R.id.nav_host_fragment, FirstMainPage())
                    }
                } //uploads user data to firebase
                .addOnFailureListener {
                    Toast.makeText(requireActivity(),"try again",Toast.LENGTH_SHORT).show()
                }//gets toast

        }

    }
}