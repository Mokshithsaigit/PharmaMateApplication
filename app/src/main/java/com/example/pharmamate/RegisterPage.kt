package com.example.pharmamate

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.content.Context
import android.content.Intent


class RegisterPage : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        val nameInput = findViewById<EditText>(R.id.name)
        val userNameInput = findViewById<EditText>(R.id.username)
        val passwordInput = findViewById<EditText>(R.id.password)
        val password2Input = findViewById<EditText>(R.id.password2)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginText = findViewById<TextView>(R.id.loginPageText)

        sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)

        registerButton.setOnClickListener {
            val name = nameInput.text.toString()
            val userName = userNameInput.text.toString()
            val password = passwordInput.text.toString()
            val password2 = password2Input.text.toString()

            if (validateRegistrationFields(name,userName, password, password2)) {
                checkAndRegisterUser(name,userName, password)
            }
        }

        loginText.setOnClickListener{
            startActivity(Intent(this,LoginPage::class.java))
            finish()
        }
    }

    private fun validateRegistrationFields(name: String,username: String, password: String, password2: String): Boolean {
        return if (name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty()) {
            if (password == password2) {
                true
            } else {
                showToast("Enter password correctly")
                false
            }
        } else {
            showToast("Please fill all fields")
            false
        }
    }

    private fun checkAndRegisterUser(name: String,username: String, password: String) {
        val existingUsername = sharedPreferences.getString("username", "")

        if (existingUsername == username) {
            showToast("User already exists")
        } else {
            // Register new user
            saveUserData(name,username, password)
            showToast("Registration successful")
            startActivity(Intent(this,LoginPage::class.java))
            finish()
            // Navigate to login page or other appropriate screen
        }
    }

    private fun showToast(s: String) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show()
    }

    private fun saveUserData(name:String,username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("username", username)
        editor.putString("password", password)  // Consider hashing for security
        editor.apply()
    }
}
