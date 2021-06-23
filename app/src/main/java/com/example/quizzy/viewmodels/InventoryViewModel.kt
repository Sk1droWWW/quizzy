package com.example.quizzy

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
class InventoryViewModel(private val quizDao: QuizDao) : ViewModel() {

    // Cache all items form the database using LiveData.
    val allItems: LiveData<List<Quiz>> = quizDao.getItems().asLiveData()

    /**
     * Returns true if stock is available to sell, false otherwise.
     */
    fun isStockAvailable(quiz: Quiz): Boolean {
        return (quiz.quantityInStock > 0)
    }

    /**
     * Updates an existing Quiz in the database.
     */
    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
        updateItem(updatedItem)
    }


    /**
     * Launching a new coroutine to update an quiz in a non-blocking way
     */
    private fun updateItem(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.update(quiz)
        }
    }

    /**
     * Decreases the stock by one unit and updates the database.
     */
    fun sellItem(quiz: Quiz) {
        if (quiz.quantityInStock > 0) {
            // Decrease the quantity by 1
            val newItem = quiz.copy(quantityInStock = quiz.quantityInStock - 1)
            updateItem(newItem)
        }
    }

    /**
     * Inserts the new Quiz into database.
     */
    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }

    /**
     * Launching a new coroutine to insert an quiz in a non-blocking way
     */
    private fun insertItem(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.insert(quiz)
        }
    }

    /**
     * Launching a new coroutine to delete an quiz in a non-blocking way
     */
    fun deleteItem(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.delete(quiz)
        }
    }

    /**
     * Retrieve an quiz from the repository.
     */
    fun retrieveItem(id: Int): LiveData<Quiz> {
        return quizDao.getItem(id).asLiveData()
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Returns an instance of the [Quiz] entity class with the quiz info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Quiz {
        return Quiz(
            quizName = itemName,
            quizPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    /**
     * Called to update an existing entry in the Inventory database.
     * Returns an instance of the [Quiz] entity class with the quiz info updated by the user.
     */
    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ): Quiz {
        return Quiz(
            id = itemId,
            quizName = itemName,
            quizPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class InventoryViewModelFactory(private val quizDao: QuizDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(quizDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

