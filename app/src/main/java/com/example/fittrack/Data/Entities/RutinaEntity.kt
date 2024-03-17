package com.example.fittrack.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "rutinas")
data class Rutina(
    @PrimaryKey
    val nombre: String,
    val entrenamientos: String,
    var activa: Boolean
)
