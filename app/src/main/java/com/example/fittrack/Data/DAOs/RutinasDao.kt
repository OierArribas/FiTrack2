package com.example.fittrack.Data.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fittrack.Data.Entities.Ejercicio
import com.example.fittrack.Data.Entities.Rutina
import kotlinx.coroutines.flow.Flow


@Dao
interface RutinaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Rutina)

    @Update
    suspend fun update(item: Rutina)

    @Delete
    suspend fun delete(item: Rutina)

    @Query("SELECT * from rutinas WHERE activa = 1")
    fun getItem(): Flow<Rutina>


    @Query("SELECT * from rutinas ORDER BY nombre ASC")
    fun getAllRutinas(): Flow<List<Rutina>>
}