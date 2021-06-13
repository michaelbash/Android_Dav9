 package com.example.firebaseloginapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

 class MainActivity : AppCompatActivity() {

   private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth


        val email=findViewById<TextInputEditText>(R.id.login_email)
        val password=findViewById<TextInputEditText>(R.id.login_password)
        val registerBtn=findViewById<Button>(R.id.register_button)
        val confirmPassword=findViewById<TextInputEditText>(R.id.confirm_password)

        registerBtn.setOnClickListener {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email.text.toString().trim(),
                    password.text.toString().trim()
            )
        }

        if(confirmPassword.getText().toString().equals(password.getText().toString())){
            Toast.makeText(this, "Passwords Match :)", Toast.LENGTH_SHORT).show()

        }



        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                email.toString(), password.toString()
        )

        fun CharSequence.isValidPassword(): Boolean {
            val passwordPattern = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
            val pattern = Pattern.compile(passwordPattern)
            val matcher = pattern.matcher(this)
            return matcher.matches()
        }

        fun CharSequence?.isValidEmail():Boolean{
            return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
        }




            password.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?,
                                               start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?,
                                           start: Int, before: Int, count: Int) {
                    s?.apply {
                        if (isValidPassword() && toString().length>=8) {
                            password.error == null
                        }else{
                            password.error = "invalid password."
                        }
                    }
                }
                override fun afterTextChanged(s: Editable?) {

                }
            })


        email.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?,
                                           p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?,
                                       p1: Int, p2: Int, p3: Int) {
                // check inputted text that it is a valid email address or not
                if (p0.isValidEmail()){
                    email.error = null
                }else{
                    email.error = "Invalid email."
                }
            }

            override fun afterTextChanged(p0: Editable?) { }
        })






    }
}