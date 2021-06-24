package com.example.quizzy.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity database class represents a single row in the database.
 */
@Entity(tableName = "quizzes")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quiz_id)")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val quizName: String,

    @ColumnInfo(name = "description")
    val quizDescription: String,
)
