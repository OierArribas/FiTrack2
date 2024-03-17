package com.example.fittrack.Data.Entities

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "Entrenamiento_Ejercicio",
    foreignKeys = [
        ForeignKey(entity = Ejercicio::class, parentColumns = ["nombre"], childColumns = ["ejercicio"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Entrenamiento::class, parentColumns = ["nombre"], childColumns = ["ejercicio"], onDelete = ForeignKey.CASCADE)
    ],
    )
data class EntrenamietoEjercicioCrossRef(
    val numSeries: Int,
)