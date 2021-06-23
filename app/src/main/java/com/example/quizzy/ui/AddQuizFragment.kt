package com.example.quizzy.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizzy.viewmodels.QuizViewModel
import com.example.quizzy.viewmodels.InventoryViewModelFactory
import com.example.quizzy.QuizApplication
import com.example.quizzy.database.Quiz
import com.example.quizzy.databinding.FragmentAddQuizBinding

/**
 * Fragment to add or update an quiz in the Inventory database.
 */
class AddQuizFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: QuizViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as QuizApplication).database
                .itemDao()
        )
    }
    private val navigationArgs: QuizDetailFragmentArgs by navArgs()

    lateinit var quiz: Quiz

    // Binding object instance corresponding to the fragment_add_quiz.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentAddQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.quizName.text.toString(),
            binding.quizDescription.text.toString(),
        )
    }

    /**
     * Binds views with the passed in [quiz] information.
     */
    private fun bind(quiz: Quiz) {
        binding.apply {
            quizName.setText(quiz.quizName, TextView.BufferType.SPANNABLE)
            quizDescription.setText(quiz.quizDescription, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateItem() }
        }
    }

    /**
     * Inserts the new Quiz into database and navigates up to list fragment.
     */
    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewQuiz(
                binding.quizName.text.toString(),
                binding.quizDescription.text.toString(),
            )
            val action = AddQuizFragmentDirections.actionAddQuizFragmentToItemQuizFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Updates an existing Quiz in the database and navigates up to list fragment.
     */
    private fun updateItem() {
        if (isEntryValid()) {
            viewModel.updateQuiz(
                this.navigationArgs.quizId,
                this.binding.quizName.text.toString(),
                this.binding.quizDescription.text.toString(),
            )
            val action = AddQuizFragmentDirections.actionAddQuizFragmentToItemQuizFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Called when the view is created.
     * The itemId Navigation argument determines the edit quiz  or add new quiz.
     * If the itemId is positive, this method retrieves the information from the database and
     * allows the user to update it.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.quizId
        if (id > 0) {
            viewModel.retrieveQuiz(id).observe(this.viewLifecycleOwner) { selectedItem ->
                quiz = selectedItem
                bind(quiz)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewItem()
            }
        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager
            .hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}
