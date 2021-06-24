package com.example.quizzy.database

import androidx.room.*
import com.example.quizzy.database.model.QuestionWithAnswers
import com.example.quizzy.database.model.Quiz
import com.example.quizzy.database.model.QuizWithQuestions
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface QuizDao {

    @Query("SELECT * from quiz ORDER BY name ASC")
    fun getItems(): Flow<List<Quiz>>

    @Query("SELECT * from quiz WHERE `quiz_id` = :id")
    fun getItem(id: Int): Flow<Quiz>

    @Transaction
    @Query("SELECT * FROM quiz")
    fun getQuizWithQuestions(): List<QuizWithQuestions>

    @Transaction
    @Query("SELECT * FROM question")
    fun getQuestionWithAnswers(): List<QuestionWithAnswers>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Quiz into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quiz: Quiz)

    @Update
    suspend fun update(quiz: Quiz)

    @Delete
    suspend fun delete(quiz: Quiz)
}
