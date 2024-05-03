package com.example.pharmamate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.pharmamate.ViewPager.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class BillPage : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    var viewPagerAdapter: ViewPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_page)

        val tv = findViewById<TextView>(R.id.tv)

        val intent = intent
        val list = intent.getParcelableArrayListExtra<MedicineItem>("list")

        if (list != null) {
            // Access the first item only if the list is not empty
            if (list.isNotEmpty()) {
                var sum = 0
                for(i in list){
                    sum += i.amount
                }
                // Now you can safely use the item object
                tv.text = "₹" + sum.toString()  // Example: display item details
            } else {
                // Handle the case where the list is empty
                tv.text = "No items found in the list."
            }
        } else {
            // Handle the case where the Intent doesn't have the "list" key
            tv.text = "Error: 'list' not found in the Intent."
        }

        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tabs)

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter

        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager)
    }
}


