package com.example.quizzy.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionWithAnswers(
    @Embedded
    val question: Question,

    @Relation(
        parentColumn = "questionId",
        entityColumn = "questionId"
    )
    val answers: List<Answer>
)
