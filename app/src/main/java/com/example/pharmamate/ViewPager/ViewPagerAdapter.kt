package com.example.pharmamate.ViewPager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.pharmamate.R

class ViewPagerAdapter( fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0)
            fragment = Phonepe()
        else if (position == 1)
            fragment = Paytm()
        else if (position == 2)
            fragment = Gpay()
        return fragment!!
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0)
            title = "PhonePe QR"
        else if (position == 1)
            title = "Paytm QR"
        else if (position == 2)
            title = "GPay QR"
        return title
    }
}