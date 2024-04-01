package com.example.flashcard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import kotlin.math.max

class MainActivity : AppCompatActivity() {
    private val ADD_CARD_REQUEST = 1
    private val SAVE_CARD_REQUEST = 2
    private lateinit var flashcardDatabase: FlashcardDatabase
    private var allFlashcards = mutableListOf<Flashcard>()
    private var currentCardDisplayedIndex= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        var flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        var flashcardAnswer2 = findViewById<TextView>(R.id.flashcard_answer2)
        var flashcardAnswer3 = findViewById<TextView>(R.id.flashcard_answer3)

        var bontonPlus = findViewById<View>(R.id.pbtn)
        var boutonEdit = findViewById<View>(R.id.editBtn)
        var botnNext= findViewById<ImageView>(R.id.rightbtn)
        var deletebtn=findViewById<ImageView>(R.id.poubtn)


        flashcardDatabase = FlashcardDatabase(this)
        flashcardDatabase.initFirstCard()
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        if (allFlashcards.size > 0) {
            flashcardQuestion.text = allFlashcards[0].question
            flashcardAnswer.text = allFlashcards[0].answer
            flashcardAnswer2.text = allFlashcards[0].wrongAnswer1
            flashcardAnswer3.text = allFlashcards[0].wrongAnswer2

        }

//        flashcardAnswer.visibility = View.INVISIBLE
//
//        flashcardQuestion.setOnClickListener {
//            flashcardQuestion.visibility = View.INVISIBLE
//            flashcardAnswer.visibility = View.VISIBLE
//        }
//
//        flashcardAnswer.setOnClickListener {
//            flashcardAnswer.visibility = View.INVISIBLE
//            flashcardQuestion.visibility = View.VISIBLE
//        }

        bontonPlus.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
        }

        boutonEdit.setOnClickListener {
            val (question, answer, wrongAnswer1, wrongAnswer2, uuid) = allFlashcards[currentCardDisplayedIndex]

            val intent = Intent(this, AddCardActivity::class.java)

            intent.putExtra("question", question)
            intent.putExtra("answer1", answer)
            intent.putExtra("answer2", wrongAnswer1)
            intent.putExtra("answer3", wrongAnswer2)
            intent.putExtra("uuid", uuid)
            startActivityForResult(intent, ADD_CARD_REQUEST)
        }

        botnNext.setOnClickListener {
            if (allFlashcards.isEmpty()) {
                return@setOnClickListener
            }

            currentCardDisplayedIndex = getRandomNumber(0,allFlashcards.size-1)

            if (currentCardDisplayedIndex >= allFlashcards.size) {
                currentCardDisplayedIndex = 0
               // Snackbar.make(findViewById(R.id.editBtn), "No Cards", Snackbar.LENGTH_SHORT).show()

            }

            val (question, answer,wrongAnswer1,wrongAnswer2) = allFlashcards[currentCardDisplayedIndex]

            flashcardQuestion.text = question
            flashcardAnswer.text = answer
            flashcardAnswer2.text = wrongAnswer1
            flashcardAnswer3.text = wrongAnswer2
        }

        deletebtn.setOnClickListener {
            val currentQuestion = flashcardQuestion.text.toString()
            flashcardDatabase.deleteCard(currentQuestion)

            allFlashcards = flashcardDatabase.getAllCards().toMutableList()

            // Vérifier s'il reste des cartes
            if (allFlashcards.isNotEmpty()) {
                // Afficher la carte précédente (si disponible)
                currentCardDisplayedIndex = max(0, currentCardDisplayedIndex - 1)
                val (question, answer,wrongAnswer1,wrongAnswer2) = allFlashcards[currentCardDisplayedIndex]
                flashcardQuestion.text = question
                flashcardAnswer.text = answer
                flashcardAnswer2.text = wrongAnswer1
                flashcardAnswer3.text = wrongAnswer2

            } else {
                // S'il n'y a plus de cartes, afficher un état vide
                flashcardQuestion.text = ""
                flashcardAnswer.text = ""
            }
        }
    }

    fun getRandomNumber(minNumber: Int, maxNumber: Int): Int {
        return (minNumber..maxNumber).random() // generated random from 0 to 10 included
    }

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data: Intent? = result.data
        if (data != null) { // Check that we have data returned
            val question = data.getExtras()?.getString("question")
            val answer = data.getExtras()?.getString("answer1")
            val answer2 = data.getExtras()?.getString("answer2")
            val answer3 = data.getExtras()?.getString("answer3")


            // Log the value of the strings for easier debugging
            Log.i("MainActivity", "question: $question")
            Log.i("MainActivity", "answer: $answer")
            Log.i("MainActivity", "answer: $answer2")
            Log.i("MainActivity", "answer: $answer3")


            // Display newly created flashcard
            findViewById<TextView>(R.id.flashcard_question).text = question
            findViewById<TextView>(R.id.flashcard_answer).text = answer
            findViewById<TextView>(R.id.flashcard_answer2).text = answer2
            findViewById<TextView>(R.id.flashcard_answer3).text = answer3

        } else {
            Log.i("MainActivity", "Returned null data from AddCardActivity")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        var flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        var flashcardAnswer2 = findViewById<TextView>(R.id.flashcard_answer2)
        var flashcardAnswer3 = findViewById<TextView>(R.id.flashcard_answer3)

        if (requestCode == ADD_CARD_REQUEST && resultCode == Activity.RESULT_OK) {
            val question = data?.getStringExtra("question")
            val answer = data?.getStringExtra("answer1")
            val answer2 = data?.getStringExtra("answer2")
            val answer3 = data?.getStringExtra("answer3")

            flashcardQuestion.text = question
            flashcardAnswer.text = answer
            flashcardAnswer2.text = answer2
            flashcardAnswer3.text = answer3

        }
    }
}
