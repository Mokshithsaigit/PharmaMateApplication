package com.example.pharmamate

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginPage : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val usernameInput = findViewById<EditText>(R.id.username)
        val passwordInput = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signupText = findViewById<TextView>(R.id.signupText)

        sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)

        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            validateCredentials(username, password)
        }

        signupText.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateCredentials(username: String, password: String) {
        val storedName = sharedPreferences.getString("name", "")
        val storedUsername = sharedPreferences.getString("username", "")
        val storedPassword = sharedPreferences.getString("password", "")

        if (username == storedUsername && password == storedPassword) {
            // Login successful!
            val intent = Intent(this, HomePage::class.java)
            intent.putExtra("name",storedName)
            intent.putExtra("username",storedUsername)
            startActivity(intent)
            finish()
        } else {
            // Login failed
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }
}
