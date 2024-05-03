package com.example.pharmamate

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmamate.Database.MyDatabaseHelper
import com.example.pharmamate.Fragments.MedicineAdapter

import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import java.io.Serializable

class BillingScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private val uniqueIdList = ArrayList<String>()
    private val namesList = ArrayList<String>()
    private val priceList = ArrayList<Int>()
    private val quantityList = ArrayList<Int>()
    private val mfgList = ArrayList<String>()
    private val expList = ArrayList<String>()
    private val set = mutableSetOf<String>()

    private lateinit var myDB: MyDatabaseHelper
    private lateinit var billingAdapter: BillingAdapter

    private val launcher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Result")

            val unqCode = result.contents.toString().trim()

//            Log.d("Message from dialog", unqCode)
//            Log.d("Unique code", unqCode)
//
//            val cursor : Cursor? = myDB.getOneMedicine(unqCode)
//
//            if (cursor != null) {
//                if (cursor.moveToFirst()) { // Ensure cursor is positioned correctly
//                    uniqueIdList.add(cursor.getString(0))
//                    namesList.add(cursor.getString(1))
//                    priceList.add(cursor.getInt(2))
//                    quantityList.add(cursor.getInt(3))
//                    mfgList.add(cursor.getString(4))
//                    expList.add(cursor.getString(5))
//
//                    billingAdapter.notifyDataSetChanged()
//
//                    builder.setMessage(result.contents)
//                } else {
//                    builder.setMessage("Item not found in database")
//                }
//                cursor.close()
//            }

            if(!set.contains(unqCode)){
                set.add(unqCode)
                Log.d("Message from dialog", unqCode)
                Log.d("Unique code", unqCode)

                val cursor : Cursor? = myDB.getOneMedicine(unqCode)

                if (cursor != null) {
                    if (cursor.moveToFirst()) { // Ensure cursor is positioned correctly
                        uniqueIdList.add(cursor.getString(0))
                        namesList.add(cursor.getString(1))
                        priceList.add(cursor.getInt(2))
                        quantityList.add(cursor.getInt(3))
                        mfgList.add(cursor.getString(4))
                        expList.add(cursor.getString(5))

                        billingAdapter.notifyDataSetChanged()

                        builder.setMessage(result.contents)
                    } else {
                        builder.setMessage("Item not found in database")
                    }
                    cursor.close()
                }
            }else{
                builder.setMessage("Item is already in the billing list")
            }

            builder.setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billing_screen)

        val scanBtn = findViewById<Button>(R.id.scanBtn)
        val billButton = findViewById<Button>(R.id.billButton)
        recyclerView = findViewById(R.id.recyclerView)

        myDB = MyDatabaseHelper(this)


        scanBtn.setOnClickListener {
            val scanOptions = ScanOptions()
            scanOptions.setBeepEnabled(true)
            scanOptions.setOrientationLocked(true)
            scanOptions.setCaptureActivity(CaptureAct::class.java)
            launcher.launch(scanOptions)
        }

        billingAdapter = BillingAdapter(this,this,recyclerView,uniqueIdList,namesList,priceList,quantityList,mfgList,expList)
        recyclerView.adapter = billingAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        billButton.setOnClickListener {
            val medicineList = mutableListOf<MedicineItem>()
            for (i in 0 until uniqueIdList.size) {
                medicineList.add(billingAdapter.getMedicineItem(i))
            }
            val intent = Intent(this,BillPage::class.java)
            intent.putExtra("list",medicineList as Serializable)
            startActivity(intent)
        }

    }
}
