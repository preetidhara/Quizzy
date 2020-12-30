package com.example.quizzy

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzy.adapters.OptionAdapter
import com.example.quizzy.model.Question
import com.example.quizzy.model.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.option_item.*

class QuestionActivity : AppCompatActivity() {


    var quizzes : MutableList<Quiz>? = null
    var question: MutableMap<String, Question>? = null
    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setUpFirestore()
        setUpEventListener()
    }

    private fun setUpEventListener() {


        btnNext.setOnClickListener {
            index++
            bindViews()
        }

        btnSubmit.setOnClickListener {
            Log.d("FINALQUIZ", question.toString())

            val intent = Intent(this, ResultActivity::class.java)
            val json  = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("quizzez").whereEqualTo("title", date)
                    .get()
                    .addOnSuccessListener {
                        if(it != null && !it.isEmpty){
                            Log.d("FINALQUIZ", it.toObjects(Quiz::class.java).toString())
                            quizzes = it.toObjects(Quiz::class.java)
                            question = quizzes!![0].question
                            bindViews()
                        }
                    }
        }
    }

    private fun bindViews() {

        btnSubmit.visibility = View.GONE

        if(index == question!!.size) { // last question
            btnSubmit.visibility = View.VISIBLE
            btnNext.visibility=View.INVISIBLE
        }


        val question = question!!["question$index"]
        question?.let {
            description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            optionList.layoutManager = LinearLayoutManager(this)
            optionList.adapter = optionAdapter
            optionList.setHasFixedSize(true)
        }
    }
}
