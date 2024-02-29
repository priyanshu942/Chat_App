package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.sign

class LogIn : AppCompatActivity() {
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var login:Button
    lateinit var signup: TextView
private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()
        mAuth=FirebaseAuth.getInstance()
        email=findViewById(R.id.email)
        password=findViewById(R.id.pass)
        login=findViewById(R.id.login)
        signup=findViewById(R.id.signup)
        login.setOnClickListener {
            val eml=email.text.toString()
            val pass=password.text.toString()
            login(eml,pass)
        }

    }
    fun login( eml:String, pass:String)
    {
        mAuth.signInWithEmailAndPassword(eml, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                   Toast.makeText(this@LogIn,"Logged In Successfully",Toast.LENGTH_SHORT).show()
                    val intent=Intent(this@LogIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                   Toast.makeText(this@LogIn,"Please check Crediantial Once",Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun sgn(view: View) {
        val intent= Intent(this,SignUp::class.java)
        startActivity(intent)
    }
}