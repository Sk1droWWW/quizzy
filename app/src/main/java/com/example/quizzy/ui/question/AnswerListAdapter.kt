package com.example.quizzy.ui.question

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzy.database.model.Answer
import com.example.quizzy.databinding.AnswerListItemBinding
import kotlin.properties.Delegates

class AnswerListAdapter : RecyclerView.Adapter<AnswerListAdapter.AnswerViewHolder>() {

    var answers: List<Answer> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // This keeps track of the currently selected position
    private var selectedPosition by Delegates
        .observable(-1) { _, oldPos, newPos ->
            if (newPos in answers.indices) {
                notifyItemChanged(oldPos)
                notifyItemChanged(newPos)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        return AnswerViewHolder(
            AnswerListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun getItemCount(): Int = answers.size

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        if (position in answers.indices) {
            holder.bind(answers[position], position == selectedPosition)
            holder.itemView.setOnClickListener { selectedPosition = position }
        }
    }

    inner class AnswerViewHolder(private val binding: AnswerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(answer: Answer, selected: Boolean) {
            with(selected) {
                binding.answerText.text = answer.answerText
                binding.answerChecked.isChecked = selected
            }
        }
    }
}