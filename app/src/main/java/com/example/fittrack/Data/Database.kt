package com.example.fittrack.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fittrack.Data.DAOs.EjercicioDao
import com.example.fittrack.Data.Entities.Ejercicio


@Database(
    entities = [Ejercicio::class],
    version = 1,
    exportSchema = false
)

abstract class Database : RoomDatabase(){
    abstract fun EjercicioDao(): EjercicioDao

}