package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class messageAdapter(val context: Context,val messageList:ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val item_Recive=1
    val item_Sent=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==1)
        {
            val view:View=LayoutInflater.from(context).inflate(R.layout.recived_message,parent,false)
            return ReciveViewHolder(view)
        }
        else{

            val view:View=LayoutInflater.from(context).inflate(R.layout.sendlayout,parent,false)
            return SendViewHolder(view)

        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass==SendViewHolder::class.java)
        {
            val currentMessage=messageList[position]
            val viewHolder=holder as SendViewHolder
            holder.sentMessage.text=currentMessage.message
        }
        else{
            val currentMessage=messageList[position]
            val viewHolder=holder as ReciveViewHolder
            holder.reciveMessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId))
        {
            return item_Sent
        }
        else{
            return item_Recive
        }

    }
    class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage=itemView.findViewById<TextView>(R.id.txtsnd)
    }
    class ReciveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val reciveMessage=itemView.findViewById<TextView>(R.id.rcvmsg)
    }
}