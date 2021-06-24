package com.example.quizzy.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.quizzy.database.model.Quiz
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface QuizDao {

    @Query("SELECT * from quiz ORDER BY name ASC")
    fun getItems(): Flow<List<Quiz>>

    @Query("SELECT * from quiz WHERE id = :id")
    fun getItem(id: Int): Flow<Quiz>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Quiz into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quiz: Quiz)

    @Update
    suspend fun update(quiz: Quiz)

    @Delete
    suspend fun delete(quiz: Quiz)
}
