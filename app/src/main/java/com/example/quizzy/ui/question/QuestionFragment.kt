package com.example.quizzy.ui.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzy.QuizApplication
import com.example.quizzy.database.model.Question
import com.example.quizzy.database.model.Quiz
import com.example.quizzy.databinding.FragmentQuestionBinding
import com.example.quizzy.viewmodels.QuestionViewModel
import com.example.quizzy.viewmodels.QuestionViewModelFactory

class QuestionFragment : Fragment() {
    private val viewModel: QuestionViewModel by activityViewModels {
        QuestionViewModelFactory(
            (activity?.application as QuizApplication).database.itemDao()
        )
    }

    private val navigationArgs: QuestionFragmentArgs by navArgs()
    private val adapter: AnswerListAdapter = AnswerListAdapter()

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!
//    private lateinit viewModel: QuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Binds views with the passed in quiz database.
     */
    private fun bind(quiz: Quiz) {
        /*binding.apply {
            quizName.text = quiz.quizName
            quizDescription.text = quiz.quizDescription
            editItem.setOnClickListener { editItem() }
        }*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AnswerListAdapter(
            // TODO: - implement radio button listener
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        viewModel.allQuestionWithAnswers.observe(viewLifecycleOwner, Observer { answers ->
            val quizId = navigationArgs.quizId
            var questionList: List<Question>? = null

            if (quizId > 0) {
                questionList = viewModel.retrieveQuestions(quizId).value
            }
            val questionId = questionList?.get(0)?.questionId

            val currentQuestionAnswers = viewModel.retrieveAnswers(questionId)?.value

            if (currentQuestionAnswers != null) {
                adapter.answers = currentQuestionAnswers
            }
        })

    }
}