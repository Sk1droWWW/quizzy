package com.example.quizzy.viewmodels

import androidx.lifecycle.*
import com.example.quizzy.database.QuizDao
import com.example.quizzy.database.model.Answer
import com.example.quizzy.database.model.Question
import com.example.quizzy.database.model.QuestionWithAnswers
import kotlinx.coroutines.launch

class QuestionViewModel(private val quizDao: QuizDao) : ViewModel(){

    // Cache all items form the database using LiveData.
    val allQuestionWithAnswers: LiveData<List<QuestionWithAnswers>> =
        quizDao.getQuestionWithAnswers().asLiveData()

    val allAnswers: LiveData<List<Answer>> = quizDao
        .getAnswersByQuestion(currentQuestion.value?.questionId).asLiveData()
    lateinit var currentQuestion: LiveData<Question>

    /**
     * Retrieve an questions from the repository.
     */
    fun retrieveQuestions(id: Int): LiveData<List<Question>> {
        return quizDao.getQuestionsByQuiz(id).asLiveData()
    }

    /**
     * Retrieve an question from the repository.
     */
    fun retrieveQuestion(questionId: Int):  LiveData<Question> {
        currentQuestion = quizDao.getQuestion(questionId).asLiveData()
        return currentQuestion
    }


    /**
     * Retrieve an question from the repository.
     */
    fun retrieveAnswers(id: Int?): LiveData<List<Answer>>? {
        return id?.let { quizDao.getAnswersByQuestion(it).asLiveData() }
    }

    /**
     * Launching a new coroutine to insert an question in a non-blocking way
     */
    private fun insertQuestion(question: Question) {
        viewModelScope.launch {
           // TODO:
        }
    }

    /**
     * Launching a new coroutine to delete an question in a non-blocking way
     */
    private fun deleteQuestion(question: Question) {
        viewModelScope.launch {
            // TODO:
        }
    }

    /**
     * Updates an existing Quiz in the database.
     */
    fun updateQuestion() {
        // TODO: -
    }



}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class QuestionViewModelFactory(private val quizDao: QuizDao) : ViewModelProvider.Factory {

    // TODO
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionViewModel(quizDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}