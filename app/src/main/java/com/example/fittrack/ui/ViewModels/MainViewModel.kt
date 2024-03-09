package com.example.fittrack.ui.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.fittrack.Data.Repos.EjerciciosRepository
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.Data.Entities.Ejercicio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val ejerciciosRepository: EjerciciosRepository,

    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var ejercicioForm by mutableStateOf(false)



    val ejercicios = ejerciciosRepository.getAllItemsStream()




    //----------------------------------------------------------//
    // -----------------------Ejercicios------------------------//
    //----------------------------------------------------------//
    fun OpenCloseEjercicioForm(value: Boolean){
        ejercicioForm = value
    }
    fun addEjercicio(ejercicio: Ejercicio) {
        viewModelScope.launch {
            ejerciciosRepository.insertItem(ejercicio)
        }
    }
    fun submitEjercio(ejercicio: Ejercicio){
        this.addEjercicio(ejercicio)
        this.OpenCloseEjercicioForm(false)
    }
    fun deleteEjercicio(ejercicio: Ejercicio){
        viewModelScope.launch {
            ejerciciosRepository.deleteItem(ejercicio)
        }
    }


}

