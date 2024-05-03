package com.example.pharmamate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

class FeedbackPage : AppCompatActivity() {

    private val file = "feedback.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_page)

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val feedback = findViewById<EditText>(R.id.feedback)
        val submit = findViewById<Button>(R.id.submit)
        val viewFeedback = findViewById<TextView>(R.id.viewFeedback)
        val viewBtn = findViewById<Button>(R.id.viewBtn)
        val clear = findViewById<Button>(R.id.clear)
//        submitButton.setOnClickListener{
//            val totalStars = "Total Stars " + simpleRatingBar.numStars
//            val rating = " rating" + simpleRatingBar.rating
//            Toast.makeText(this,""" $totalStars $rating""".trimIndent(), Toast.LENGTH_LONG).show()
//
//        }

        submit.setOnClickListener {
            val rating = ratingBar.rating
            val feed = feedback.text.toString()

            val data = " Rating : " + " ${rating} " + "\n" + " Feedback : " + feed + "\n" + "\n" + "\n"

            try {
                val fOut =openFileOutput(file, MODE_APPEND)
                fOut.write(data.toByteArray())
                fOut.close()
                feedback.text.clear()
                Toast.makeText(this,"Feedback saved Successfully",Toast.LENGTH_SHORT).show()
            }catch (e : Exception){
                Toast.makeText(this,"Could not save details",Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        viewBtn.setOnClickListener {
            try {
                val fin =openFileInput(file)
                var c : Int
                var temp = ""
                while(fin.read().also { c = it } != -1){
                    temp += c.toChar().toString()
                }

                viewFeedback.text = temp
                fin.close()
                Toast.makeText(this,"File read successfully",Toast.LENGTH_SHORT).show()

            }catch (e : Exception){
                Toast.makeText(this,"Unable to read file",Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        clear.setOnClickListener {
            feedback.text.clear()
            viewFeedback.text = ""
        }

    }
}