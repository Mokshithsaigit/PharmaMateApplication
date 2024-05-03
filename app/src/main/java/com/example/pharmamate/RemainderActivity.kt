package com.example.pharmamate

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.pharmamate.AlarmReceiver
import com.google.android.material.navigation.NavigationView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class RemainderActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var selectTimeTextView: TextView
    private lateinit var setAlarmButton: Button
    private lateinit var cancelAlarmButton: Button

    private lateinit var drawerLayout : DrawerLayout


    private lateinit var timePicker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remainder)

        selectTimeTextView = findViewById(R.id.selectTime)
        setAlarmButton = findViewById(R.id.setAlarm)
        cancelAlarmButton = findViewById(R.id.cancelAlarm)

        createNotificationChannel()

        selectTimeTextView.setOnClickListener {
            showTimePickerDialog()
        }

        setAlarmButton.setOnClickListener {
            setAlarm()
        }

        cancelAlarmButton.setOnClickListener {
            cancelAlarm()
        }

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbarhome)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.navigation_drawer)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> null
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "akchannel"
            val desc = "Channel for Alarm Manager"
            val imp = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("androidknowledge", name, imp)
            channel.description = desc

            val notificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun showTimePickerDialog() {
        timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        timePicker.show(supportFragmentManager, "androidknowledge")
        timePicker.addOnPositiveButtonClickListener {
            val timeText = if (timePicker.hour > 12) {
                "${String.format("%02d", timePicker.hour - 12)}:${String.format("%02d", timePicker.minute)} PM"
            } else {
                "${timePicker.hour}:${timePicker.minute} AM"
            }
            selectTimeTextView.text = timeText
        }
    }


    private fun setAlarm() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 123, intent,
            PendingIntent.FLAG_IMMUTABLE)
        val currentTimeMillis = System.currentTimeMillis()
        if (timePicker.hour > 12){
            val selectedTimeMillis = calculateAlarmTime(timePicker.hour - 12, timePicker.minute, currentTimeMillis)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (30 * 1000),
                pendingIntent
            )
            Log.d("Time", "${currentTimeMillis}")
            Log.d("Curr Time", "${System.currentTimeMillis()}")
            Log.d("Sub", "${currentTimeMillis-System.currentTimeMillis()}")
        }else{
            val selectedTimeMillis = calculateAlarmTime(timePicker.hour, timePicker.minute, currentTimeMillis)

            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (30 * 1000),
                pendingIntent
            )

            Log.d("Time", "${currentTimeMillis}")
            Log.d("Curr Time", "${System.currentTimeMillis()}")
            Log.d("Sub", "${currentTimeMillis-System.currentTimeMillis()}")
        }


        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show()
        Log.d("AlarmReceiver", "${timePicker.hour}")
    }


    private fun calculateAlarmTime(hour: Int, minute: Int, currentTimeMillis: Long): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        return if (calendar.timeInMillis <= currentTimeMillis) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            Log.d("time : ",calendar.timeInMillis.toString())
            calendar.timeInMillis

        } else {
            Log.d("time : ",calendar.timeInMillis.toString())
            calendar.timeInMillis
        }
    }


    private fun cancelAlarm() {
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 123, intent,
            PendingIntent.FLAG_IMMUTABLE)

        if (::alarmManager.isInitialized.not()) {
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show()
        }
}