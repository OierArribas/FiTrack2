package com.example.fittrack.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fittrack.Data.DAOs.EjercicioDao
import com.example.fittrack.Data.DAOs.EntrenamientoDao
import com.example.fittrack.Data.DAOs.RutinaDao
import com.example.fittrack.Data.Entities.Ejercicio
import com.example.fittrack.Data.Entities.Entrenamiento
import com.example.fittrack.Data.Entities.Rutina


@Database(
    entities = [Ejercicio::class, Entrenamiento::class, Rutina::class],
    version = 3,
    exportSchema = false
)

abstract class Database : RoomDatabase(){
    abstract fun EjercicioDao(): EjercicioDao
    abstract fun EntrenamientosDao(): EntrenamientoDao
    abstract fun RutinasDao(): RutinaDao

}