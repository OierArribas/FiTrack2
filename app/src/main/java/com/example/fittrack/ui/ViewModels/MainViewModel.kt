package com.example.fittrack.ui.ViewModels

import com.example.fittrack.ui.Data.EjerciciosRepository
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val ejerciciosRepository: EjerciciosRepository,

    savedStateHandle: SavedStateHandle,
) : ViewModel() {


    //val ejercicios = ejerciciosRepository.getAllItemsStream().map { it. }


}

