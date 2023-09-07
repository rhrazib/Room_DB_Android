package com.razibsoft.roomdbandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        editText = findViewById(R.id.editText)
        recyclerView = findViewById(R.id.recyclerView)
        saveButton = findViewById(R.id.saveBtn)
        // Initialize RecyclerView and adapter
        adapter = UserAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Observe user data changes and update the RecyclerView
        userViewModel.allUsers.observe(this) { users ->
            adapter.submitList(users)
        }

        // Save user button click handler
        saveButton.setOnClickListener {
            saveUser()
        }
    }

    private fun saveUser() {
        val userName = editText.text.toString()
        if (userName.isNotBlank()) {
            val newUser = User(0, userName, "")
            userViewModel.insertUser(newUser)
            editText.text.clear()
        }
    }
}
