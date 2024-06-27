package com.example.pharmamate

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class HomePage : AppCompatActivity() {
    var currentImage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val nameField = findViewById<TextView>(R.id.name)
        val idField = findViewById<TextView>(R.id.id)
        val inventory = findViewById<Button>(R.id.inventory)
        val billing = findViewById<Button>(R.id.billing)
        val feedback = findViewById<Button>(R.id.feedback)
        val remainder = findViewById<Button>(R.id.remainder)

        val name = intent.getStringExtra("name")
        if(name != null){
            nameField.text = name
        }

        val id = intent.getStringExtra("username")
        if(id != null){
            idField.text = id
        }

        val next = findViewById<ImageView>(R.id.next)
        val prev = findViewById<ImageView>(R.id.prev)
        val image = findViewById<ImageView>(R.id.image)

        val quote1 = R.drawable.quote1
        val quote2 = R.drawable.quote2
        val quote3 = R.drawable.quote3
        val quote4 = R.drawable.quote4

        val images = listOf(quote1,quote2,quote3,quote4)

        prev.setOnClickListener{
            currentImage = (images.size + currentImage - 1) % images.size
            val uri = Uri.parse("android.resource://${packageName}/${images[currentImage]}")
            image.setImageURI(uri)
        }

        next.setOnClickListener{
            currentImage = (images.size + currentImage + 1) % images.size
            val uri = Uri.parse("android.resource://${packageName}/${images[currentImage]}")
            image.setImageURI(uri)
        }

        inventory.setOnClickListener{
            startActivity(Intent(this,ManageInventory::class.java))
        }

        billing.setOnClickListener{
            startActivity(Intent(this,BillingScreen::class.java))
        }

        feedback.setOnClickListener {
            startActivity(Intent(this,FeedbackPage::class.java))
        }

        remainder.setOnClickListener {
//            startActivity(Intent(this,RemainderActivity::class.java))
        }

    }
}