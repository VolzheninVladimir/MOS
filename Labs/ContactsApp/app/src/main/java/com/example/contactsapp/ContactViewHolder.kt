package com.example.contactsapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.Contact

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val nameText: TextView = itemView.findViewById(R.id.contactName)
    private val phoneText: TextView = itemView.findViewById(R.id.contactPhone)

    fun bind(contact: Contact) {
        nameText.text = contact.name
        phoneText.text = contact.phone
    }
}
