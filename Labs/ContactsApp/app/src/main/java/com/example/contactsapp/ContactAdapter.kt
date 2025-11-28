package com.example.contactsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.Contact

class ContactAdapter(private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size
}