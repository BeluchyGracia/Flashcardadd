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
    private lateinit var flashcardAnswer: TextViewgit 
    private  lateinit var textView2: TextView
    private  lateinit var textView3: TextView
    private lateinit var  textView4: TextView

    private lateinit var bontonPlus: ImageView
    private lateinit var boutonEdit: ImageView

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data: Intent? = result.data
        if (result.resultCode == Activity.RESULT_OK && data != null) {
            val question = data.getStringExtra("question")
            val answer = data.getStringExtra("answer1")
            val answer2 = data.getStringExtra("answer2")
            val answer3 = data.getStringExtra("answer3")
            val answer4 = data.getStringExtra("answer4")

            flashcardQuestion.text = question
            flashcardAnswer.text = answer
            textView2.text = answer2
            textView3.text = answer3
            textView4.text = answer4
        } else {
            Log.i("AddCardActivity", "Save operation cancelled or no data returned")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashcardQuestion = findViewById(R.id.flashcard_question)
        flashcardAnswer = findViewById(R.id.flashcard_answer)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        textView4 = findViewById(R.id.textView4)

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
            val answer2 = textView2.text.toString()
            val answer3 = textView3.text.toString()
            val answer4 = textView4.text.toString()

            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra("question", question)
            intent.putExtra("answer1", answer1)
            intent.putExtra("answer2", answer2)
            intent.putExtra("answer3", answer3)
            intent.putExtra("answer4", answer4)

            startActivityForResult(intent, ADD_CARD_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CARD_REQUEST && resultCode == Activity.RESULT_OK) {
            val question = data?.getStringExtra("question")
            val answer = data?.getStringExtra("answer1")
            val answer1 = data?.getStringExtra("answer2")
            val answer2 = data?.getStringExtra("answer3")
            val answer3 = data?.getStringExtra("answer4")

            flashcardQuestion.text = question
            flashcardAnswer.text = answer
            textView2.text = answer1
            textView3.text = answer2
            textView4.text = answer3

        }
    }
}
