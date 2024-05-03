package com.example.pharmamate.Fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import com.example.pharmamate.AlarmReceiver
import com.example.pharmamate.R



class QuestionsFragment : Fragment() {

    private lateinit var timeEditText: EditText
    private lateinit var startAlarmButton: Button
    private lateinit var cancelAlarmButton: Button
    private lateinit var alarmManager: AlarmManager
    private val pendingIntentRequestCode = 234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_questions, container, false)

        timeEditText = view.findViewById(R.id.time) // Replace with your EditText's ID
        startAlarmButton = view.findViewById(R.id.startButton) // Replace with your button's ID
        cancelAlarmButton = view.findViewById(R.id.cancel_btn) // Replace with your button's ID

        val alarmIntent = Intent(requireContext(), Receiver::class.java) // Replace with your BroadcastReceiver class
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            pendingIntentRequestCode,
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        startAlarmButton.setOnClickListener {
            var i = timeEditText.text.toString().toInt()
            alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(
                AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +
                        (i * 1000), pendingIntent
            )
            Toast.makeText(requireActivity(), "Alarm set in $i seconds", Toast.LENGTH_LONG).show()

        }


        cancelAlarmButton.setOnClickListener {
            alarmManager.cancel(pendingIntent)
            Toast.makeText(requireContext(), "Alarm Cancelled", Toast.LENGTH_SHORT).show()
        }

        return view
    }


}
