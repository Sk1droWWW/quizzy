package com.example.quizzy.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answer")
data class Answer(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "answer_id")
    val answerId: Int,

    @ColumnInfo(name = "question_id")
    val questionId: Int,

    @ColumnInfo(name = "answer_text")
    val answerText: String,

    @ColumnInfo(name = "correct")
    val correct: Boolean,
)
