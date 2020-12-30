package com.example.quizzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity() {
    lateinit var handler: Handler
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth= FirebaseAuth.getInstance()


        Clickhere2.setOnClickListener {
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        BtnLogin.setOnClickListener {
            LoginUser()
        }
    }

    private fun LoginUser() {
        val email=EmailAddress.text.toString()
        val password=Password.text.toString()




       firebaseAuth.signInWithEmailAndPassword(email,password)
           .addOnCompleteListener {
               if (it.isSuccessful){
                   // Delay and Start Activity
                   val inflater=layoutInflater.inflate(R.layout.progressbar,null)
                   AlertDialog.Builder(this).setView(inflater).show()

                   handler=Handler()
                   handler.postDelayed({


                       val intent=Intent(this,MainActivity::class.java)
                       startActivity(intent)
                       finish()

                   } , 3000)
               }
               else{
                   Toast.makeText(this,"Authentication Wrong",Toast.LENGTH_SHORT).show()
               }
           }

    }
}