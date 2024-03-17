package com.example.fittrack.Data.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fittrack.Data.Entities.Ejercicio
import com.example.fittrack.Data.Entities.Entrenamiento
import kotlinx.coroutines.flow.Flow

@Dao
interface EntrenamientoDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Entrenamiento)

    @Update
    suspend fun update(item: Entrenamiento)

    @Delete
    suspend fun delete(item: Entrenamiento)

    @Query("SELECT * from entrenamientos WHERE nombre = :nombre1")
    fun getItem(nombre1: String): Flow<Entrenamiento>

    @Query("SELECT * from entrenamientos ORDER BY nombre ASC")
    fun getAllEntrenamientos(): Flow<List<Entrenamiento>>

    @Query("SELECT * from entrenamientos ORDER BY nombre ASC")
    fun getAllEjerciciosEntrenamiento(): Flow<List<Entrenamiento>>

}