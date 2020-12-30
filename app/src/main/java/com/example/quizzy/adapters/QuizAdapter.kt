package com.example.quizzy.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzy.QuestionActivity
import com.example.quizzy.R
import com.example.quizzy.model.Quiz
import com.example.quizzy.utils.ColorPicker
import com.example.quizzy.utils.IconPicker
import kotlinx.android.synthetic.main.quiz_item.view.*
import org.w3c.dom.Text

class QuizAdapter(val context: Context,val quizzes:List<Quiz>): RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

   inner class ViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {
       var title=itemView.findViewById<TextView>(R.id.title)
       var IconView=itemView.findViewById<ImageView>(R.id.Icon)
       var CardContainer=itemView.findViewById<CardView>(R.id.cardContainer)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.quiz_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text=quizzes[position].title
        holder.CardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.IconView.setImageResource(IconPicker.getIcon())
        holder.CardContainer.setOnClickListener(){
           // Toast.makeText(context, "Date", Toast.LENGTH_SHORT).show()
            val intent=Intent(context,QuestionActivity::class.java)
            intent.putExtra("DATE",quizzes[position].title)
            context.startActivity(intent)
        }



    }

    override fun getItemCount(): Int {
      return quizzes.size
    }
}