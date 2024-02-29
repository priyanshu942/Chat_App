package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var create: Button
    lateinit var name:EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        email=findViewById(R.id.email)
        password=findViewById(R.id.pass)
       create=findViewById(R.id.create)
        name=findViewById(R.id.cpass)
        mAuth= FirebaseAuth.getInstance()
        create.setOnClickListener {
            val eml=email.text.toString()
            val pass=password.text.toString()
            val name=name.text.toString()
            signUp(name,eml,pass)
        }


    }

    private fun signUp(name:String,eml: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(eml, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name,eml, mAuth.currentUser?.uid!!)
                    Toast.makeText(this@SignUp,"Created Account",Toast.LENGTH_SHORT).show()
                    val intent= Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp,"Some Error Occured",Toast.LENGTH_SHORT).show()
                    // If sign in fails, display a message to the user.

                }
            }
    }




    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbref=FirebaseDatabase.getInstance().getReference()
        mDbref.child("user").child(uid).setValue(User(name,email,uid))
    }
}