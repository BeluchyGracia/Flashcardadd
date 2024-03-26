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

    private lateinit var flashcardQuestion: TextView
    private lateinit var flashcardAnswer: TextView
    private lateinit var bontonPlus: ImageView
    private lateinit var boutonEdit: ImageView

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data: Intent? = result.data
        if (result.resultCode == Activity.RESULT_OK && data != null) {
            val question = data.getStringExtra("question")
            val answer = data.getStringExtra("answer1")
            flashcardQuestion.text = question
            flashcardAnswer.text = answer
        } else {
            Log.i("AddCardActivity", "Save operation cancelled or no data returned")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashcardQuestion = findViewById(R.id.flashcard_question)
        flashcardAnswer = findViewById(R.id.flashcard_answer)
        bontonPlus = findViewById(R.id.pbtn)
        boutonEdit = findViewById(R.id.editBtn)

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
            resultLauncher.launch(intent)
        }

        boutonEdit.setOnClickListener {
            val question = flashcardQuestion.text.toString()
            val answer1 = flashcardAnswer.text.toString()

            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra("question", question)
            intent.putExtra("answer1", answer1)
            startActivityForResult(intent, ADD_CARD_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CARD_REQUEST && resultCode == Activity.RESULT_OK) {
            val question = data?.getStringExtra("question")
            val answer = data?.getStringExtra("answer1")
            flashcardQuestion.text = question
            flashcardAnswer.text = answer
        }
    }
}
