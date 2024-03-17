package com.example.fittrack.Data.Repos

import com.example.fittrack.Data.DAOs.RutinaDao
import com.example.fittrack.Data.DAOs.EntrenamientoDao
import com.example.fittrack.Data.Entities.Rutina
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface RutinaRepository {

    fun getAllItemsStream(): Flow<List<Rutina>>

    fun getItemStream(): Flow<Rutina?>

    suspend fun insertItem(item: Rutina)

    suspend fun deleteItem(item: Rutina)

    suspend fun updateItem(item: Rutina)
}


@Singleton
class RutinasRepository @Inject constructor(
    private val rutinaDao: RutinaDao
) : RutinaRepository {

    override fun getAllItemsStream(): Flow<List<Rutina>> = rutinaDao.getAllRutinas()

    override fun getItemStream(): Flow<Rutina?> = rutinaDao.getItem()

    override suspend fun insertItem(item: Rutina) = rutinaDao.insert(item)

    override suspend fun deleteItem(item: Rutina) = rutinaDao.delete(item)

    override suspend fun updateItem(item: Rutina) = rutinaDao.update(item)
}