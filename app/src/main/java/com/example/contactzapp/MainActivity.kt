package com.example.contactzapp

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.contactzapp.databinding.ActivityMainBinding
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var binding: ActivityMainBinding
    private val contacts = mutableListOf<Contact>()
    private lateinit var adapter: ContactAdapter

    // Predefined contacts to keep it simple
    private val contactList = mutableListOf(
        Contact("Trinity", "123-456-7890"),
        Contact("Morpheus", "987-654-3210"),
        Contact("Neo", "111-222-3333")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflating the layout with viewbinding first
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setting up the recyclerview with adapter
        contactAdapter = ContactAdapter(contactList,
            onEdit = { position -> editContact(position) },
            onDelete = { position -> deleteContact(position) },
            onMessage = { /* Placeholder for future message action */ }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = contactAdapter
        }

        //button listener for adding new contact
        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            //validate input
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                val newContact = Contact(name, phone)
                contacts.add(newContact)
                adapter.notifyItemInserted(contacts.size - 1)

                // Clear input fields
                binding.etName.text?.clear()
                binding.etPhone.text?.clear()
            } else {
                Toast.makeText(this, "Please enter both name and phone", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to handle edit action
    private fun editContact(position: Int) {
        val contact = contactList[position]

        // Show alert dialog to edit name and phone
        val editDialog = AlertDialog.Builder(this)
        val inputView = layoutInflater.inflate(R.layout.dialog_edit_contact, null)
        val nameInput = inputView.findViewById<EditText>(R.id.editName)
        val phoneInput = inputView.findViewById<EditText>(R.id.editPhone)

        nameInput.setText(contact.name)
        phoneInput.setText(contact.phone)

        editDialog.setView(inputView)
            .setTitle("Edit Contact")
            .setPositiveButton("Save") { _, _ ->
                contact.name = nameInput.text.toString()
                contact.phone = phoneInput.text.toString()
                contactAdapter.notifyItemChanged(position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteContact(position: Int) {
        contactList.removeAt(position)
        contactAdapter.notifyItemRemoved(position)
    }
}
