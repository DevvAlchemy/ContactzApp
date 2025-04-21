package com.example.contactzapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.contactzapp.databinding.ActivityMainBinding
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var binding: ActivityMainBinding

    // Predefined contacts to keep it simple
    private val contactList = mutableListOf(
        Contact("Trinity", "123-456-7890"),
        Contact("Morpheus", "987-654-3210"),
        Contact("Neo", "111-222-3333")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        contactAdapter = ContactAdapter(contactList,
            onEdit = { position -> editContact(position) },
            onDelete = { position -> deleteContact(position) },
            onMessage = { /* Placeholder for future message action */ }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = contactAdapter
        }
    }

    private fun editContact(position: Int) {
        val contact = contactList[position]

        // Show alert dialog to edit name and phone
        val editDialog = AlertDialog.Builder(this)
        val inputView = layoutInflater.inflate(R.layout.dialog_edit_contact, null)
        val nameInput = inputView.findViewById<android.widget.EditText>(R.id.editName)
        val phoneInput = inputView.findViewById<android.widget.EditText>(R.id.editPhone)

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
