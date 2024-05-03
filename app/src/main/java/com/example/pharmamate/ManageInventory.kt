package com.example.pharmamate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pharmamate.Fragments.AddFragment
import com.example.pharmamate.Fragments.ItemsFragment
import com.example.pharmamate.Fragments.QuestionsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ManageInventory : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_inventory)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        replaceFragment(ItemsFragment())

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.add -> replaceFragment(AddFragment())
                R.id.item -> replaceFragment(ItemsFragment())
                R.id.questions -> replaceFragment(QuestionsFragment())

                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}