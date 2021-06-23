package com.example.quizzy.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

/**
 * Entity database class represents a single row in the database.
 */
@Entity
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val quizName: String,

    @ColumnInfo(name = "description")
    val quizDescription: String,
)
