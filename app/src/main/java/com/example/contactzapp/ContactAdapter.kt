package com.example.contactzapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactzapp.databinding.ItemContactBinding

// Adapter class to manage how contact items are shown in RecyclerView
class ContactAdapter(
    private val contacts: List<Contact>,
    private val onEdit: (position: Int) -> Unit,
    private val onDelete: (position: Int) -> Unit,
    private val onMessage: (position: Int) -> Unit
    ) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    // ViewHolder holds references to views in each list item
    inner class ContactViewHolder(val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Create a ViewHolder by inflating the item_contact layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ContactViewHolder(binding)
    }

    // Bind data from the contact list to the views
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.binding.tvName.text = contact.name
        holder.binding.tvPhone.text = contact.phone
        // more click listeners here for future edit/delete/message actions
    }

    // Tell RecyclerView how many items to show
    override fun getItemCount() = contacts.size
}
