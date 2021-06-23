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
    @ColumnInfo(name = "price")
    val quizPrice: Double,
    @ColumnInfo(name = "quantity")
    val quantityInStock: Int,
)
/**
 * Returns the passed in price in currency format.
 */
fun Quiz.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance().format(quizPrice)