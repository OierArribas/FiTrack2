package com.example.fittrack.ui.Data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface ItemsRepository {

    fun getAllItemsStream(): Flow<List<Ejercicio>>

    fun getItemStream(nombre: String): Flow<Ejercicio?>

    suspend fun insertItem(item: Ejercicio)

    suspend fun deleteItem(item: Ejercicio)

    suspend fun updateItem(item: Ejercicio)
}


@Singleton
class EjerciciosRepository @Inject constructor(
    private val ejercicioDao: EjercicioDao
) : ItemsRepository {

    override fun getAllItemsStream(): Flow<List<Ejercicio>> = ejercicioDao.getAllItems()

    override fun getItemStream(nombre: String): Flow<Ejercicio?> = ejercicioDao.getItem(nombre)

    override suspend fun insertItem(item: Ejercicio) = ejercicioDao.insert(item)

    override suspend fun deleteItem(item: Ejercicio) = ejercicioDao.delete(item)

    override suspend fun updateItem(item: Ejercicio) = ejercicioDao.update(item)
}

