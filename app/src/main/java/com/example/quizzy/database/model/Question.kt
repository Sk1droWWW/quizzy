package com.example.quizzy.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "questions")/*,
foreignKeys = @ForeignKey(entity = Quiz::class,))*/
data class Question(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "question_id")
    var questionId: Int,

    @ColumnInfo(name = "quiz_id")
    var quizId: Int,

    /*@ColumnInfo(name = "multiple_answers")
    val multipleAnswers: Boolean,*/

    @ColumnInfo(name = "question_text")
    val text: String
)
