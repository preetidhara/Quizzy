package com.example.quizzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    lateinit var firebaseAuth:FirebaseAuth
    lateinit var handler:Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseAuth= FirebaseAuth.getInstance()

        BtnSignUp.setOnClickListener {
            SignUpUser()
        }
        Clickhere.setOnClickListener {
            val intent=Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun SignUpUser() {
        val email=edtEmailAddress.text.toString()
        val password=edtPassword.text.toString()
        val Cpassword=edtCPassword.text.toString()

        if (email.isEmpty()&& password!=Cpassword){
            Toast.makeText(this,"Something Went wrong",Toast.LENGTH_SHORT).show()
        }
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        // Delay and Start Activity
                        val inflater=layoutInflater.inflate(R.layout.progressbar,null)
                        AlertDialog.Builder(this).setView(inflater).show()
                        handler=Handler()
                        handler.postDelayed({

                            val intent=Intent(this,Login::class.java)
                            startActivity(intent)
                            finish()

                        } , 5000)
                    }
                    else
                    {
                        Toast.makeText(this,"Something Went wrong",Toast.LENGTH_SHORT).show()
                    }
                }


    }
}