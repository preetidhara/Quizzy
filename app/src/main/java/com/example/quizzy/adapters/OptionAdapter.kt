package com.example.quizzy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzy.R
import com.example.quizzy.model.Question

class OptionAdapter(val context: Context, val question: Question ): RecyclerView.Adapter<OptionAdapter.ViewHolder>() {

    private var options:List<String> = listOf(question.option1,question.option2,question.option3,question.option4)

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var OptionView=itemView.findViewById<TextView>(R.id.quiz_option)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.option_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.OptionView.text = options[position]
        holder.itemView.setOnClickListener {
            question.userAnswer = options[position]
            notifyDataSetChanged()
        }

       /* for(i in options){
            if (question.userAnswer!=question.answer){
                holder.itemView.setBackgroundResource(R.drawable.option_item_wrong_bg)
            }
        }

        */


          if (question.userAnswer==options[position]){
                holder.itemView.setBackgroundResource(R.drawable.option_item_correct_bg)
                //Toast.makeText(context, "Selected", Toast.LENGTH_SHORT).show()
            }
            else{
                holder.itemView.setBackgroundResource(R.drawable.option_item_bg)

            }




    }

    override fun getItemCount(): Int {
        return options.size
    }
}