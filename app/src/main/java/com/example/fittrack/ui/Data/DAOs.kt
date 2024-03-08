package com.example.fittrack.ui.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EjercicioDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Ejercicio)

    @Update
    suspend fun update(item: Ejercicio)

    @Delete
    suspend fun delete(item: Ejercicio)

    @Query("SELECT * from ejercicios WHERE nombre = :nombre")
    fun getItem(nombre: String): Flow<Ejercicio>

    @Query("SELECT * from ejercicios ORDER BY nombre ASC")
    fun getAllItems(): Flow<List<Ejercicio>>

}