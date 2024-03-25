package com.example.flashcard

import android.app.Activity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView


class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        val questionText = findViewById<EditText>(R.id.questions)
        val answertext = findViewById<EditText>(R.id.answer)
        val saveBtn = findViewById<ImageView>(R.id.sbtn)

        findViewById<ImageView>(R.id.cbtn).setOnClickListener {
            finish()
        }

        saveBtn.setOnClickListener {
            val question = questionText.text.toString()
            val answer = answertext.text.toString()
            val resultIntent = Intent().apply {
                putExtra("question", question)
                putExtra("answer", answer)
            }
            setResult(RESULT_OK, resultIntent)

            // Fermer AddCardActivity
            finish()
        }
    }
}