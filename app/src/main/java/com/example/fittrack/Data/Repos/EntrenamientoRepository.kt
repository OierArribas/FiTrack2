package com.example.fittrack.Data.Repos

import com.example.fittrack.Data.DAOs.EjercicioDao
import com.example.fittrack.Data.DAOs.EntrenamientoDao
import com.example.fittrack.Data.Entities.Ejercicio
import com.example.fittrack.Data.Entities.Entrenamiento
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface EntrenamientoRepository {

    fun getAllItemsStream(): Flow<List<Entrenamiento>>

    fun getItemStream(nombre: String): Flow<Entrenamiento?>

    suspend fun insertItem(item: Entrenamiento)

    suspend fun deleteItem(item: Entrenamiento)

    suspend fun updateItem(item: Entrenamiento)
}


@Singleton
class EntrenamientosRepository @Inject constructor(
    private val entrenamientoDao: EntrenamientoDao
) : EntrenamientoRepository {

    override fun getAllItemsStream(): Flow<List<Entrenamiento>> = entrenamientoDao.getAllEjerciciosEntrenamiento()

    override fun getItemStream(nombre: String): Flow<Entrenamiento?> = entrenamientoDao.getItem(nombre)

    override suspend fun insertItem(item: Entrenamiento) = entrenamientoDao.insert(item)

    override suspend fun deleteItem(item: Entrenamiento) = entrenamientoDao.delete(item)

    override suspend fun updateItem(item: Entrenamiento) = entrenamientoDao.update(item)
}