package com.example.quizzy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzy.QuizApplication
import com.example.quizzy.database.model.Question
import com.example.quizzy.database.model.Quiz
import com.example.quizzy.databinding.FragmentAddQuestionBinding
import com.example.quizzy.databinding.FragmentQuestionBinding
import com.example.quizzy.ui.question.AnswerListAdapter
import com.example.quizzy.viewmodels.QuestionViewModel
import com.example.quizzy.viewmodels.QuestionViewModelFactory

class AddQuestionFragment : Fragment() {

    private val viewModel: QuestionViewModel by activityViewModels {
        QuestionViewModelFactory(
            (activity?.application as QuizApplication).database.itemDao()
        )
    }

    private val navigationArgs: AddQuestionFragmentArgs by navArgs()

    private var _binding: FragmentAddQuestionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Binds views with the passed in [question] information.
     */
    private fun bind(question: Question?) {
//        val questionString = "Question $question.questionId"
        val questionString = "Question 1"


        binding.apply {
            questionNumberTitle.text = questionString
            questionText.setText(question?.text, TextView.BufferType.SPANNABLE)
            addAnswer.setOnClickListener { addAnswer()}
            saveAction.setOnClickListener { updateItem() }
        }
    }

    private fun addAnswer() {

    }

    private fun updateItem() {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AnswerListAdapter()

        binding.answersList.layoutManager = LinearLayoutManager(context)
        binding.answersList.adapter = adapter

        val currentQuestionId = navigationArgs.questionId

        bind(viewModel.retrieveQuestion(currentQuestionId).value)

        viewModel.allAnswers.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

    }

}