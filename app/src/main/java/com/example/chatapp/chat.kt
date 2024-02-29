package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.ktx.Firebase

class chat : AppCompatActivity() {
    private lateinit var Rcview:RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var Imagebtn:ImageButton
    private lateinit var messageAdapter:messageAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var mDbref:DatabaseReference
    var reciverRoom:String?=null
    var senderRoom:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        Rcview=findViewById(R.id.chatRec)
        messageBox=findViewById(R.id.message)
        Imagebtn=findViewById(R.id.snd)

        val name=intent.getStringExtra("name")
        val reciveruid=intent.getStringExtra("uid")
        val senderuid= FirebaseAuth.getInstance().currentUser?.uid
        mDbref=FirebaseDatabase.getInstance().getReference()
        senderRoom=reciveruid + senderuid
         reciverRoom=senderuid + reciveruid
        supportActionBar?.title =name
        messageList= ArrayList()
        messageAdapter= messageAdapter(this,messageList)

        Rcview.layoutManager=LinearLayoutManager(this)
        Rcview.adapter=messageAdapter
        //data adding to recycler view
        mDbref.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()
                for (postSnapshot in snapshot.children){
                    val message=postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
//message adding to db
        Imagebtn.setOnClickListener {
            val message=messageBox.text.toString()
            val messageObject= Message(message, senderuid.toString())
            mDbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbref.child("chats").child(reciverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }
    }
}