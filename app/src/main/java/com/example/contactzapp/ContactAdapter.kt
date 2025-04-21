package com.example.contactzapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactzapp.databinding.ItemContactBinding

class ContactAdapter(
    private val contacts: List<Contact>,
    val onEdit: (Int) -> Unit,
    val onDelete: (Int) -> Unit,
    val onMessage: (Int) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(val binding: ItemContactBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        with(holder.binding) {
            nameText.text = contact.name
            phoneText.text = contact.phone

            editBtn.setOnClickListener { onEdit(position) }
            deleteBtn.setOnClickListener { onDelete(position) }
            messageBtn.setOnClickListener { onMessage(position) }
        }
    }

    override fun getItemCount(): Int = contacts.size
}
