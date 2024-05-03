package com.example.pharmamate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pharmamate.Database.MyDatabaseHelper
import com.example.pharmamate.Fragments.AddFragment


class EditScreen : AppCompatActivity() {

    private lateinit var myDB : MyDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_screen)


        val uniqueIdInput = findViewById<TextView>(R.id.uniqueId)
        val nameInput = findViewById<EditText>(R.id.name)
        val priceInput = findViewById<EditText>(R.id.price)
        val quantityInput = findViewById<EditText>(R.id.quantity)
        val mfgInput = findViewById<EditText>(R.id.mfg)
        val expInput = findViewById<EditText>(R.id.exp)

        val saveChanges = findViewById<Button>(R.id.saveBtn)
        val deleteBtn = findViewById<Button>(R.id.delete)

        val uniqueId = intent.getStringExtra("uniqueId")
        val name = intent.getStringExtra("name")
        val price = intent.getIntExtra("price", 0)
        val quantity = intent.getIntExtra("quantity", 0)
        val mfg = intent.getStringExtra("mfg")
        val exp = intent.getStringExtra("exp")

        uniqueIdInput.text = uniqueId
        nameInput.setText(name)
        priceInput.setText(price.toString())
        quantityInput.setText(quantity.toString())
        mfgInput.setText(mfg)
        expInput.setText(exp)


        saveChanges.setOnClickListener {

            val uniqueIdChanged = findViewById<TextView>(R.id.uniqueId)
            val nameInputChanged = findViewById<EditText>(R.id.name)
            val priceInputChanged = findViewById<EditText>(R.id.price)
            val quantityInputChanged = findViewById<EditText>(R.id.quantity)
            val mfgInputChanged = findViewById<EditText>(R.id.mfg)
            val expInputChanged = findViewById<EditText>(R.id.exp)

            if (uniqueIdChanged.text.toString().isNotEmpty() &&
                nameInputChanged.text.toString().isNotEmpty() &&
                priceInputChanged.text.toString().isNotEmpty() &&
                quantityInputChanged.text.toString().isNotEmpty() &&
                mfgInputChanged.text.toString().isNotEmpty() &&
                expInputChanged.text.toString().isNotEmpty()
            ) {
                myDB = MyDatabaseHelper(this)
                if (myDB.updateMedicines(
                        uniqueIdChanged.text.toString(),
                        nameInputChanged.text.toString(),
                        priceInputChanged.text.toString(),
                        quantityInputChanged.text.toString(),
                        mfgInputChanged.text.toString(),
                        expInputChanged.text.toString()
                    )
                ) {
                    Toast.makeText(this, "Modified details saved successfully", Toast.LENGTH_LONG)
                        .show()
                    setResult(RESULT_OK)
                    startActivity(Intent(this, ManageInventory::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Error occurred while saving changes into DB",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            }

        }


        deleteBtn.setOnClickListener {
            val uniqueId = findViewById<TextView>(R.id.uniqueId).text.toString()
            myDB = MyDatabaseHelper(this)

            val builder = AlertDialog.Builder(this)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this medicine?")
                .setPositiveButton("Yes") { dialog, which ->
                    // Delete if confirmed
                    if (myDB.deleteOneMedicine(uniqueId)) {
                        Toast.makeText(
                            this,
                            "Medicine deleted successfully from Inventory",
                            Toast.LENGTH_LONG
                        ).show()
                        setResult(RESULT_OK)
                        startActivity(Intent(this, ManageInventory::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Error occurred while deleting the item",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
            builder.create().show()
        }
    }
}