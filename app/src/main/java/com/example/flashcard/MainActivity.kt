package com.example.flashcard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {
    private val ADD_CARD_REQUEST = 1
    private val SAVE_CARD_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        val bontonPlus = findViewById<ImageView>(R.id.pbtn)

        val mainLayout = findViewById<RelativeLayout>(R.id.main_layout)
        flashcardAnswer.visibility = View.INVISIBLE
        flashcardQuestion.setOnClickListener {
            flashcardQuestion.visibility = View.INVISIBLE
            flashcardAnswer.visibility = View.VISIBLE
        }
        flashcardAnswer.setOnClickListener {
            flashcardAnswer.visibility = View.INVISIBLE
            flashcardQuestion.visibility = View.VISIBLE
        }

        flashcardQuestion.visibility = View.VISIBLE

        bontonPlus.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            startActivityForResult(intent, SAVE_CARD_REQUEST)
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                val question = data.getStringExtra("question")
                val answer = data.getStringExtra("answer")


                flashcardQuestion.text = question
                flashcardAnswer.text = answer
            } else {
                Log.i("AddCardActivity", "Save operation cancelled or no data returned")
            }
        }


        bontonPlus.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        val bontonPlus = findViewById<ImageView>(R.id.pbtn)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CARD_REQUEST && resultCode == Activity.RESULT_OK) {
            val question = data?.getStringExtra("question")
            val answer = data?.getStringExtra("answer")
            findViewById<TextView>(R.id.flashcard_question).text = question
            findViewById<TextView>(R.id.flashcard_answer).text = answer
        }
    }
}



