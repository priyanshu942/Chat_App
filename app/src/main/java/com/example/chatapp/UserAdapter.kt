package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context, val usrList:ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view:View=LayoutInflater.from(context).inflate(R.layout.user_layou,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
      return usrList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currntUser=usrList[position]
        holder.txtName.text=currntUser.name
        holder.itemView.setOnClickListener {
            val intent=Intent(context,chat::class.java)
            intent.putExtra("name",currntUser.name)
            intent.putExtra("uid", currntUser.uid)
            context.startActivity(intent)
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtName=itemView.findViewById<TextView>(R.id.txtName)
    }
}