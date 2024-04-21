package com.example.fittrack.ui.ViewModels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fittrack.login.FiTrackUser
import com.example.fittrack.login.UsersRepo
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

interface LoginResultHandler {
    fun onLoginResult(success: Boolean)

}
class LoginViewModel(
    private val usersRepo: UsersRepo,
): ViewModel() {

    var loggedUser = "Guest"
    var loggedUserPasword = ""
    lateinit var loggedUserPhoto: Bitmap
    var hasPhoto = false
    var loginSuccess = false
    var respuesta = ""

    var registerSucces = false



    fun requestLogin(user: String, password: String, handler: LoginResultHandler) {

        viewModelScope.launch {
            val fiTrackUser = usersRepo.getUser(username = user, password = password).first().data
            loggedUser = fiTrackUser?.username ?: ""
            loggedUserPasword = fiTrackUser?.hashedpassword ?: ""


            if (loggedUser.length > 1) {
                withContext(Dispatchers.Main) {
                    loginSuccess = true
                    loggedUserPasword = password

                    val responsePhoto = fiTrackUser?.foto
                    if (responsePhoto != null) {
                        _bitmaps.value += decodeBitmap(responsePhoto)
                        loggedUserPhoto = decodeBitmap(responsePhoto)
                        hasPhoto = true
                    }


                    handler.onLoginResult(true)
                    subscribeTopic()

                }
            } else {
                withContext(Dispatchers.Main) {
                    respuesta = "Usuario o contraseña incorrectos"
                    handler.onLoginResult(false)
                }
            }
        }
    }



    fun requestRegister(user: String, password: String) {
        var fiTrackUser: FiTrackUser?

        viewModelScope.launch {
            fiTrackUser = usersRepo.addUser(username = user, password = password).first().data
            loggedUser = fiTrackUser?.username ?: ""
            if (loggedUser.length > 1) {
                withContext(Dispatchers.Main) {
                    registerSucces = true
                }
            } else {
                withContext(Dispatchers.Main) {
                    respuesta = "No se ha podido registrar el usuario"
                }
            }

            Log.i("loginSucces", loginSuccess.toString())
            Log.i("loggedUser", loggedUser)
        }

    }


    // ---------------- CAMARA -----------------


    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()

    fun onTakePhoto(bitmap: Bitmap){

        var fiTrackUser: FiTrackUser?
        val encodedBitmap = encodeBitmap(bitmap)
        Log.i("Bitmap", encodedBitmap)


        viewModelScope.launch {
            Log.i("Bitmap", "enviar peticion")
            usersRepo.uploadPhoto(loggedUser, encodedBitmap).first().data
            hasPhoto = true

            fiTrackUser = usersRepo.getUser(loggedUser,loggedUserPasword).first().data


            val responsePhoto = fiTrackUser?.foto
            if (responsePhoto != null) {
                _bitmaps.value += decodeBitmap(responsePhoto)
                loggedUserPhoto = decodeBitmap(responsePhoto)
            }

        }


    }



    fun encodeBitmap(bitmap: Bitmap, quality: Int = 100): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decodeBitmap(encodedImage: String): Bitmap {
        val decodedByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
    }


    //------------------------ FIREBASE -------------------------


    fun subscribeTopic(){
        viewModelScope.launch {
            val token = Firebase.messaging.token.await()
            Log.i("Firebase Token", token)
            //usersRepo.subscribeTopic(token)
            val response = usersRepo.subscribeTopic(token).first().data
            Log.i("Firebase Token", response.toString())
        }
    }

}
