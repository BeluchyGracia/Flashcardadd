package com.example.flashcard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView

class AddCardActivity : AppCompatActivity() {
    private lateinit var questionText: EditText
    private lateinit var answerText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        // Initialisation des vues
        questionText = findViewById(R.id.questions)
        answerText = findViewById(R.id.answer)
        val saveBtn = findViewById<ImageView>(R.id.sbtn)

        // Récupération des données reçues depuis MainActivity
        val receivedQuestion = intent.getStringExtra("question")
        val receivedAnswer = intent.getStringExtra("answer1")

        // Affichage des données dans les EditText
        questionText.setText(receivedQuestion)
        answerText.setText(receivedAnswer)

        // Gestion du clic sur le bouton de sauvegarde
        saveBtn.setOnClickListener {
            val question = questionText.text.toString()
            val answer = answerText.text.toString()

            // Création de l'Intent de résultat pour renvoyer les données à MainActivity
            val resultIntent = Intent().apply {
                putExtra("question", question)
                putExtra("answer1", answer)
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
