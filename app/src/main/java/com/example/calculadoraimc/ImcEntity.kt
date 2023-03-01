package com.example.calculadoraimc

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History-Table")
data class ImcEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val date: String,
    val peso: String,
    val altura: String,
    val imc: String
)