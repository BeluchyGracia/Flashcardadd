package com.example.flashcard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView

class AddCardActivity : AppCompatActivity() {
    private lateinit var questionText: EditText
    private lateinit var answer: EditText
    private lateinit var answer1: EditText
    private lateinit var answer2: EditText
    private lateinit var answer3: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        // Initialisation des vues
        questionText = findViewById(R.id.questions)
        answer = findViewById(R.id.answer)
        answer1 = findViewById(R.id.answer1)
        answer2 = findViewById(R.id.answer2)
        answer3 = findViewById(R.id.answer3)
        val saveBtn = findViewById<ImageView>(R.id.sbtn)

        // Récupération des données reçues depuis MainActivity
        val receivedQuestion = intent.getStringExtra("question")
        val receivedAnswer = intent.getStringExtra("answer1")
        val receivedAnswer1 = intent.getStringExtra("answer2")
        val receivedAnswer2 = intent.getStringExtra("answer3")
        val receivedAnswer3 = intent.getStringExtra("answer4")

        // Affichage des données dans les EditText
        questionText.setText(receivedQuestion)
        answer.setText(receivedAnswer)
        answer1.setText(receivedAnswer1)
        answer2.setText(receivedAnswer2)
        answer3.setText(receivedAnswer3)

        // Gestion du clic sur le bouton de sauvegarde
        saveBtn.setOnClickListener {
            val question = questionText.text.toString()
            val answer = answer.text.toString()
            val answer1 = answer1.text.toString()
            val answer2 = answer2.text.toString()
            val answer3 = answer3.text.toString()

            // Création de l'Intent de résultat pour renvoyer les données à MainActivity
            val resultIntent = Intent().apply {
                putExtra("question", question)
                putExtra("answer1", answer)
                putExtra("answer2", answer1)
                putExtra("answer3", answer2)
                putExtra("answer4", answer3)
            }
            setResult(Activity.RESULT_OK, resultIntent)

            // Fermeture de l'activité AddCardActivity
            finish()
        }

        // Gestion du clic sur le bouton de fermeture
        findViewById<ImageView>(R.id.cbtn).setOnClickListener {
            finish()
        }
    }
}
