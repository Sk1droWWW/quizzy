package com.example.quizzy

import android.app.Application
import com.example.quizzy.database.QuizDatabase


class QuizApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: QuizDatabase by lazy { QuizDatabase.getDatabase(this) }
}
