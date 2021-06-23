package com.example.quizzy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.quizzy.database.Quiz
import com.example.quizzy.database.QuizDao
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the Inventory repository and an up-to-date list of all items.
 *
 */
class QuizViewModel(private val quizDao: QuizDao) : ViewModel() {

    // Cache all items form the database using LiveData.
    val allItems: LiveData<List<Quiz>> = quizDao.getItems().asLiveData()


    /**
     * Updates an existing Quiz in the database.
     */
    fun updateQuiz(
        quizId: Int,
        quizName: String,
        quizDescription: String,
    ) {
        val updatedItem = getUpdatedItemEntry(quizId, quizName, quizDescription)
        updateQuiz(updatedItem)
    }


    /**
     * Launching a new coroutine to update an quiz in a non-blocking way
     */
    private fun updateQuiz(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.update(quiz)
        }
    }

    /**
     * Inserts the new Quiz into database.
     */
    fun addNewQuiz(quizName: String, quizDescription: String) {
        val newItem = getNewQuizEntry(quizName, quizDescription)
        insertQuiz(newItem)
    }

    /**
     * Launching a new coroutine to insert an quiz in a non-blocking way
     */
    private fun insertQuiz(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.insert(quiz)
        }
    }

    /**
     * Launching a new coroutine to delete an quiz in a non-blocking way
     */
    fun deleteQuiz(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.delete(quiz)
        }
    }

    /**
     * Retrieve an quiz from the repository.
     */
    fun retrieveQuiz(id: Int): LiveData<Quiz> {
        return quizDao.getItem(id).asLiveData()
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(itemName: String, quizDescription: String): Boolean {
        if (itemName.isBlank() || quizDescription.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Returns an instance of the [Quiz] entity class with the quiz info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewQuizEntry(quizName: String, quizDescription: String): Quiz {
        return Quiz(
            quizName = quizName,
            quizDescription = quizDescription,
        )
    }

    /**
     * Called to update an existing entry in the Inventory database.
     * Returns an instance of the [Quiz] entity class with the quiz info updated by the user.
     */
    private fun getUpdatedItemEntry(
        quizId: Int,
        quizName: String,
        quizDescription: String,
    ): Quiz {
        return Quiz(
            id = quizId,
            quizName = quizName,
            quizDescription = quizDescription,
        )
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class InventoryViewModelFactory(private val quizDao: QuizDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(quizDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

