package com.example.fittrack.login

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.net.PasswordAuthentication


interface UsersRepo {
    suspend fun getUser(username: String, password: String): Flow<Result<FiTrackUser>>
    suspend fun addUser(username: String, password: String): Flow<Result<FiTrackUser>>
    suspend fun uploadPhoto(username: String, photoBitmapString: String): Flow<Result<FiTrackUser>>
    suspend fun subscribeTopic(token: String): Flow<Result<FiTrackUser>>
}

class UserRepoImpl (
    private val api: Api
): UsersRepo {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getUser(
        username: String,
        password: String
    ): Flow<Result<FiTrackUser>> {

        return flow{
            val userFromApi = try {
                api.getUser(username, password)

            }catch (e: IOException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }

            emit(Result.Succes(userFromApi))




        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun addUser(username: String, password: String): Flow<Result<FiTrackUser>> {
        return flow{
            val userFromApi = try {
                val userData = UserData(username, password)
                api.adduser(userData)

            }catch (e: IOException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }

            emit(Result.Succes(userFromApi))




        }

    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun uploadPhoto(
        username: String,
        photoBitmapString: String
    ): Flow<Result<FiTrackUser>> {
        return flow{
            val userFromApi = try {
                api.uploadPhoto(username, photoBitmapString )

            }catch (e: IOException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }

            emit(Result.Succes(userFromApi))




        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun subscribeTopic(token: String): Flow<Result<FiTrackUser>> {

        return flow{
            val userFromApi = try {

                api.subscribeTopic(token)

            }catch (e: IOException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }catch (e: HttpException){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }catch (e: Exception){
                e.printStackTrace()
                emit(Result.Error(message = "Error loading user"))
                return@flow
            }

            emit(Result.Succes(userFromApi))




        }
    }
}

