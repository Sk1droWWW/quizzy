package com.example.quizzy.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class QuizAndQuestions(
    @Embedded
    val quiz: Quiz,

    @Relation(
        parentColumn = "quiz_id",
        entityColumn = "quiz_id"
    )
    val questions: List<Question>
)
