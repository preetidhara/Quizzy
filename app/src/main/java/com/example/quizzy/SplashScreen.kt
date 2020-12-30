package com.example.quizzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        firebaseAuth= FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser!=null){
            Toast.makeText(this,"User is already Logged in",Toast.LENGTH_SHORT).show()
            redirect("Main")
        }

        BtnStart.setOnClickListener {
            redirect("Login")
        }




    }

    private fun redirect(name: String) {
        val intent=when(name){
            "Login"->Intent(this,Login::class.java)
            "Main"-> Intent(this,MainActivity::class.java)
            else -> throw Exception("No Path Exists")
        }
        startActivity(intent)
        finish()

    }
}