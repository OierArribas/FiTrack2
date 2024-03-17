package com.example.fittrack.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "entrenamientos")
data class Entrenamiento(
    @PrimaryKey
    val nombre: String,
    val ejercicios: String
)
