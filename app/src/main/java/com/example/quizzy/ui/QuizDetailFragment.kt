package com.example.quizzy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizzy.viewmodels.QuizViewModel
import com.example.quizzy.viewmodels.InventoryViewModelFactory
import com.example.quizzy.QuizApplication
import com.example.quizzy.R
import com.example.quizzy.database.Quiz
import com.example.quizzy.databinding.FragmentQuizDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * [QuizDetailFragment] displays the details of the selected quiz.
 */
class QuizDetailFragment : Fragment() {
    private val navigationArgs: QuizDetailFragmentArgs by navArgs()
    lateinit var quiz: Quiz

    private val viewModel: QuizViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as QuizApplication).database.itemDao()
        )
    }

    private var _binding: FragmentQuizDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Binds views with the passed in quiz database.
     */
    private fun bind(quiz: Quiz) {
        binding.apply {
            quizName.text = quiz.quizName
            quizDescription.text = quiz.quizDescription
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editItem() }
        }
    }

    /**
     * Navigate to the Edit quiz screen.
     */
    private fun editItem() {
        val action = QuizDetailFragmentDirections.actionQuizDetailFragmentToAddQuizFragment(
            getString(R.string.edit_fragment_title),
            quiz.id
        )
        this.findNavController().navigate(action)
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the quiz.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    /**
     * Deletes the current quiz and navigates to the list fragment.
     */
    private fun deleteItem() {
        viewModel.deleteQuiz(quiz)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.quizId
        // Retrieve the quiz details using the itemId.
        // Attach an observer on the database (instead of polling for changes) and only update the
        // the UI when the database actually changes.
        viewModel.retrieveQuiz(id).observe(this.viewLifecycleOwner) { selectedItem ->
            quiz = selectedItem
            bind(quiz)
        }
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
