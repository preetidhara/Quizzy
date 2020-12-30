package com.example.quizzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizzy.adapters.QuizAdapter
import com.example.quizzy.model.Quiz
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var appbaToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    lateinit var firestore: FirebaseFirestore
     private var quizList = mutableListOf<Quiz>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    DummyData()
        setUPpView()
        FirebaseMessaging.getInstance().token
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(
                                "Failed",
                                "Fetching FCM registration token failed",
                                task.exception
                        )
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    val token = task.result

                    // Log and toast
                    val msg = "Success"

                    //UserUtils.UpdateToken(this, token!!)
                    Log.d("Deliver", token!!)
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                })

    }

    private fun DummyData() {
      /*  quizList.add(Quiz("12-10-20","12,54,20"))
        quizList.add(Quiz("12-10-20","12,54,20"))
        quizList.add(Quiz("12-10-20","12,54,20"))
        quizList.add(Quiz("12-10-20","12,54,20"))
        quizList.add(Quiz("12-10-20","12,54,20")) */

    }


    private fun setUPpView() {
        setUpFireStore()
        setUpDrawerLayout()
        setUpRecycleView()
        setUpDataPicker()

    }

    private fun setUpDataPicker() {
        BtnDatePicker.setOnClickListener {
            val dataPicker=MaterialDatePicker.Builder.datePicker().build()
            dataPicker.show(supportFragmentManager,"DatePicker")
            dataPicker.addOnPositiveButtonClickListener {
                Log.d("DatePicker",dataPicker.headerText)
                val intent=Intent(this,QuestionActivity::class.java)
                 intent.putExtra("DATE",dataPicker.headerText)
                startActivity(intent)

            }
            dataPicker.addOnNegativeButtonClickListener {
                Log.d("DatePicker",dataPicker.headerText)
            }
            dataPicker.addOnCancelListener {
                Log.d("DatePicker","date cancel")
            }
        }

    }

    private fun setUpFireStore() {
        firestore= FirebaseFirestore.getInstance()
        val collectionReference= firestore.collection("quizzez")
        collectionReference.addSnapshotListener { value, error ->
            if (value==null || error!=null){
                Toast.makeText(this, "Fetching Data Error", Toast.LENGTH_SHORT).show()
            }
                Log.d("Check",value?.toObjects(Quiz::class.java).toString())

                        quizList.clear()
                    quizList.addAll(value!!.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }

    }

    private fun setUpRecycleView() {
        adapter= QuizAdapter(this,quizList)
        recycleView.layoutManager=GridLayoutManager(this,2)
        recycleView.adapter=adapter

    }

    private fun setUpDrawerLayout() {
        appbaToggle= ActionBarDrawerToggle(this,MainDrawer,R.string.app_name,R.string.app_name)
        appbaToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (appbaToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}


