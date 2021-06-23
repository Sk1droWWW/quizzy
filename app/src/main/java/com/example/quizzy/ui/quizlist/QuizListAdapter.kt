package com.example.quizzy.ui.quizlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzy.database.Quiz
import com.example.quizzy.databinding.QuizListItemBinding

/**
 * [ListAdapter] implementation for the recyclerview.
 */

class QuizListAdapter(private val onItemClicked: (Quiz) -> Unit) :
    ListAdapter<Quiz, QuizListAdapter.ItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            QuizListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: QuizListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(quiz: Quiz) {
            binding.quizName.text = quiz.quizName
            binding.quizDescription.text = quiz.quizDescription
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Quiz>() {
            override fun areItemsTheSame(oldQuiz: Quiz, newQuiz: Quiz): Boolean {
                return oldQuiz === newQuiz
            }

            override fun areContentsTheSame(oldQuiz: Quiz, newQuiz: Quiz): Boolean {
                return oldQuiz.quizName == newQuiz.quizName
            }
        }
    }
}
