package com.example.pharmamate

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.pharmamate.Database.MyDatabaseHelper
import com.example.pharmamate.Fragments.MedicineAdapter
import java.util.Calendar

import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class AddingScreen : AppCompatActivity() {

    private lateinit var uniqueCodeInput : EditText
    private lateinit var nameInput : EditText
    private lateinit var priceInput : EditText
    private lateinit var quantityInput : EditText
    private lateinit var mfgInput : EditText
    private lateinit var expInput : EditText
    private lateinit var addDetailsBtn : Button
    private lateinit var camera : ImageView
    private lateinit var mfgCalender : ImageView
    private lateinit var expCalender : ImageView

    private lateinit var myDB : MyDatabaseHelper

    private val launcher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Result")
            if (result.contents != null) {
                uniqueCodeInput.setText(result.contents)
            }
            builder.setMessage(result.contents)
            builder.setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_screen)

        uniqueCodeInput = findViewById(R.id.uniqueId)
        camera = findViewById(R.id.camera)

        nameInput = findViewById(R.id.name)
        priceInput = findViewById(R.id.price)
        quantityInput = findViewById(R.id.quantity)

        mfgInput = findViewById(R.id.mfg)
        mfgCalender = findViewById(R.id.mfgCalender)

        expInput = findViewById(R.id.exp)
        expCalender = findViewById(R.id.expCalender)

        addDetailsBtn = findViewById(R.id.addDetails)


        mfgCalender.setOnClickListener{
            openCalendar(mfgInput)
        }

        expCalender.setOnClickListener{
            openCalendar(expInput)
        }

        camera.setOnClickListener{
            val scanOptions = ScanOptions()
            scanOptions.setBeepEnabled(true)
            scanOptions.setOrientationLocked(true)
            scanOptions.setCaptureActivity(CaptureAct::class.java)
            launcher.launch(scanOptions)
        }

        addDetailsBtn.setOnClickListener{
            val uniqueCode = uniqueCodeInput.text.toString().trim()
            val name = nameInput.text.toString().trim()
            val price = priceInput.text.toString().trim()
            val quantity = quantityInput.text.toString().trim()
            val mfg = mfgInput.text.toString().trim()
            val exp = expInput.text.toString().trim()

            if(uniqueCode.isNotEmpty() && name.isNotEmpty() && price.isNotEmpty() && quantity.isNotEmpty() && mfg.isNotEmpty() && exp.isNotEmpty()){
                myDB = MyDatabaseHelper(this)
                if(myDB.addMedicine(uniqueCode,name,price,quantity,mfg,exp)){
                    Toast.makeText(this,"Details saved successfully",Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                }else{
                    Toast.makeText(this,"Error occurred while saving into DB",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Please fill all the details",Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun openCalendar(editText : EditText): String? {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { view, year, monthOfYear, dayOfMonth ->
                // Handle the selected date
                val selectedDate = String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year)
                // You can return the selected date or update your EditText with it
                // return selectedDate  // Uncomment to return the date
                editText.setText(selectedDate)
            },
            year, month, day)

        datePickerDialog.show()
        return null  // Currently not returning the date, modify as needed
    }
}