package com.example.quizzy.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionWithAnswers(
    @Embedded
    val question: Question,

    @Relation(
        parentColumn = "question_id",
        entityColumn = "question_id"
    )
    val answers: List<Answer>
)
