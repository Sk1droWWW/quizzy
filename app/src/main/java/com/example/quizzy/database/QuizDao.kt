package com.example.quizzy.database

import androidx.room.*
import com.example.quizzy.database.model.*
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
    fun getQuizWithQuestions(): Flow<List<QuizWithQuestions>>

    @Transaction
    @Query("SELECT * FROM question")
    fun getQuestionWithAnswers(): Flow<List<QuestionWithAnswers>>

   /* @Query("SELECT * from question WHERE `question_id` = :id")
    fun getAnswersByQuestion(id: Int): Flow<QuestionWithAnswers>*/

     @Query("SELECT * from answer WHERE `question_id` = :id")
    fun getAnswersByQuestion(id: Int): Flow<List<Answer>>

    @Query("SELECT * from question WHERE `quiz_id` = :id")
    fun getQuestionsByQuiz(id: Int): Flow<List<Question>>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Quiz into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quiz: Quiz)

    @Update
    suspend fun update(quiz: Quiz)

    @Delete
    suspend fun delete(quiz: Quiz)
}
