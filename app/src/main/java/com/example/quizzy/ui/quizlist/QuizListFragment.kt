package com.example.quizzy.ui.quizlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizzy.*
import com.example.quizzy.databinding.FragmentQuizListBinding
import com.example.quizzy.viewmodels.QuizViewModel
import com.example.quizzy.viewmodels.QuizViewModelFactory

/**
 * Main fragment displaying details for all items in the database.
 */
class QuizListFragment : Fragment() {
    private val viewModel: QuizViewModel by activityViewModels {
        QuizViewModelFactory(
            (activity?.application as QuizApplication).database.itemDao()
        )
    }

    private var _binding: FragmentQuizListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = QuizListAdapter {
            val action =
                QuizListFragmentDirections.actionQuizListFragmentToQuizDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this.context, 2)
        binding.recyclerView.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the database
        // changes.
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = QuizListFragmentDirections.actionQuizListFragmentToAddQuizFragment(
                getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
    }
}
