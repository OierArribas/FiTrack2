package com.example.fittrack.ui.ViewModels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.fittrack.Data.Repos.EjerciciosRepository
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.Data.Entities.Ejercicio
import com.example.fittrack.Data.Entities.Entrenamiento
import com.example.fittrack.Data.Entities.Rutina
import com.example.fittrack.Data.Repos.EntrenamientoRepository
import com.example.fittrack.Data.Repos.EntrenamientosRepository
import com.example.fittrack.Data.Repos.RutinasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val ejerciciosRepository: EjerciciosRepository,
    private val entrenamientosRepository: EntrenamientosRepository,
    private val rutinasRepository: RutinasRepository,

    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var subPagina by mutableStateOf("Ejercicios")

    var ejercicioForm by mutableStateOf(false)

    var entrenamientoForm by mutableStateOf(false)



    val ejercicios = ejerciciosRepository.getAllItemsStream()

    val lista_entrenamientos = entrenamientosRepository.getAllItemsStream()

    val rutinas = rutinasRepository.getAllItemsStream()

    var rutinaActiva = rutinasRepository.getItemStream()
    //val splitActivo = getSplitActivo()





    fun changeSubPagina(subPagina: String){
        this.subPagina = subPagina
    }



    //----------------------------------------------------------//
    // -----------------------Ejercicios------------------------//
    //----------------------------------------------------------//
    fun OpenCloseEjercicioForm(value: Boolean){
        ejercicioForm = value
    }
    fun insertEjercicio(ejercicio: Ejercicio) {
        viewModelScope.launch {
            ejerciciosRepository.insertItem(ejercicio)
        }
    }
    fun submitEjercio(ejercicio: Ejercicio){
        this.insertEjercicio(ejercicio)
        this.OpenCloseEjercicioForm(false)
    }
    fun deleteEjercicio(ejercicio: Ejercicio){
        viewModelScope.launch {
            ejerciciosRepository.deleteItem(ejercicio)
        }
    }

    suspend fun getEjercicio(nombre: String): Ejercicio? {
        val lista = ejercicios.first()
        return lista.find { it.nombre == nombre }

    }

    //----------------------------------------------------------//
    // --------------Entrenamientos Form-----------------------//
    //----------------------------------------------------------//

    var selectedEjercicioEntrenamiento by mutableStateOf("")

    var listaEjercicios by mutableStateOf("")

    var listEjercicios = mutableListOf<String>()

    var nombreEntrenamiento by mutableStateOf("")

    var edicion by mutableStateOf(false)

    fun changeSelectedEE(nombre: String){
        selectedEjercicioEntrenamiento = nombre
    }
    fun OpenCloseEntrenamientosForm(value: Boolean, entrenamiento: Entrenamiento = Entrenamiento(nombre = "", ejercicios = "")){


        if (entrenamiento.nombre == ""){

            selectedEjercicioEntrenamiento = ""
            listaEjercicios = ""
            listEjercicios = listOf<String>().toMutableList()
            nombreEntrenamiento = ""
            edicion = false


        } else if (value){

            nombreEntrenamiento = entrenamiento.nombre
            listaEjercicios = entrenamiento.ejercicios
            listEjercicios = stringALista(listaEjercicios).toMutableList()
            edicion = true

        }

        entrenamientoForm = value

    }

    fun AddEjercicio(nombre: String, numSeries: String) {
        listaEjercicios = listaEjercicios + " " + nombre + " " + numSeries
        listEjercicios.add(nombre + " " + numSeries)
    }

    fun insertEntrenamiento(){

        val inputEjercicios = listEjercicios.joinToString(separator = " ")

        if (edicion) {
            viewModelScope.launch {
                entrenamientosRepository.updateItem(Entrenamiento(nombre = nombreEntrenamiento, ejercicios = inputEjercicios))

            }
        } else {
            viewModelScope.launch {
                entrenamientosRepository.insertItem(Entrenamiento(nombre = nombreEntrenamiento, ejercicios = inputEjercicios))

            }
        }


    }

    fun deleteEntrenamiento(entrenamiento: Entrenamiento){
        viewModelScope.launch {
            entrenamientosRepository.deleteItem(entrenamiento)
        }
    }

    fun getEntrenamiento(nombre: String): List<String> {
        var ejercicios = ""
        viewModelScope.launch {
            ejercicios = entrenamientosRepository.getItemStream(nombre).first()?.ejercicios.toString()
        }

        return stringALista(ejercicios)
    }

    fun borrarSubEjercicio(ejercicio: String) {
        listEjercicios.removeIf { it == ejercicio }
        listaEjercicios = concatenarLista(listEjercicios)
    }

    private fun concatenarLista(lista: List<String>): String {
        return lista.joinToString(separator = " ")
    }

    private fun stringALista(string: String): List<String> {

        val palabras = string.split(" ")


        val lista = mutableListOf<String>()


        for (i in palabras.indices step 2) {

            if (i + 1 < palabras.size) {

                lista.add("${palabras[i]} ${palabras[i + 1]}")
            } else {

                lista.add(palabras[i])
            }
        }

        return lista
    }

    fun cambiarNombreEntrenamiento(nombre: String){
        if (!edicion) {
            nombreEntrenamiento = nombre
        }

    }


    //----------------------------------------------------------//
    // ----------------------Rutinas Form-----------------------//
    //----------------------------------------------------------//

    var selectedEntrenamientoRutina by mutableStateOf("")

    var listaEntrenamientosRutina by mutableStateOf("")

    var listEntrenamientos = mutableListOf("","","","","","","")

    var nombreRutina by mutableStateOf("")

    var edicionRutina by mutableStateOf(false)

    var rutinaForm by mutableStateOf(false)

    fun OpenCloseRutinasForm(value: Boolean, rutina: Rutina = Rutina(nombre = "", entrenamientos = "", activa = false)){


        if (rutina.nombre == ""){

            selectedEntrenamientoRutina = ""
            listaEntrenamientosRutina = ""
            listEntrenamientos = mutableListOf("","","","","","","")
            nombreRutina = ""
            edicionRutina = false


        } else if (value){

            nombreRutina = rutina.nombre
            listaEntrenamientosRutina = rutina.entrenamientos
            listEntrenamientos = stringEntRutALista(listaEntrenamientosRutina)
            edicionRutina = true

        }

        rutinaForm = value

    }

    fun actualizarDia (label: Int, entrenamiento: String){
        listEntrenamientos[label] = entrenamiento
        listaEntrenamientosRutina = concatenarLista(listEntrenamientos)

    }

    fun deleteRutina(rutina: Rutina){
        viewModelScope.launch {
            rutinasRepository.deleteItem(rutina)
        }
    }

    fun insertRutina(){

        val inputEntrenamientos = concatenarLista(listEntrenamientos)

        if (edicion) {
            viewModelScope.launch {
                rutinasRepository.updateItem(Rutina(nombre = nombreRutina, entrenamientos = inputEntrenamientos, activa = false))

            }
        } else {
            viewModelScope.launch {
                rutinasRepository.insertItem(Rutina(nombre = nombreRutina, entrenamientos = inputEntrenamientos, activa = false))

            }
        }


    }

    fun activarRutina(rutinaNueva: Rutina){

        viewModelScope.launch {

            var rutinaActivaAnterior = rutinaActiva.firstOrNull()

            if (rutinaActivaAnterior != null){

                rutinaActivaAnterior.activa = false
                rutinasRepository.updateItem(rutinaActivaAnterior)

            }

            rutinasRepository.updateItem(rutinaNueva)

        }
    }

    fun stringEntRutALista(string: String): MutableList<String> {
        val lista = string.split(" ")
        var result = mutableListOf<String>()

        for ( i in 0..lista.size-1){
            result.add(i,lista[i])
        }
        return result

    }

    fun getSplitActivo(): MutableList<String> {

        var split = ""

        viewModelScope.launch {
            val rutinaAct = rutinaActiva.firstOrNull()
            if (rutinaAct != null){
                split = rutinaAct.entrenamientos

            }
        }

        return if(split == ""){
            mutableListOf("","","","","","","aaa")
        } else {
            mutableListOf("","","","","","","ssss")
        //stringEntRutALista(split)
        }


    }

    fun getEntrenamientoDiario(rutina: Rutina, dia: Int): String {
        val split = rutina.entrenamientos
        return stringALista(split)[dia]
    }

    //----------------------------------------------------------//
    // -----------------------Principal------------------------//
    //----------------------------------------------------------//

    var visualizarEntrenamientoDiario by mutableStateOf(false)

    var nombreEntrenamientoVisualizado by mutableStateOf("")

    var listaEjerciciosEntrenamientoVisualizado = mutableListOf<String>()

    fun OpenCloseVisualizacionEntrenamientoDiario(value: Boolean, entrenamiento: Entrenamiento = Entrenamiento("","")) {
        visualizarEntrenamientoDiario = value
        nombreEntrenamientoVisualizado = entrenamiento.nombre
        listaEjerciciosEntrenamientoVisualizado = stringALista(entrenamiento.ejercicios).toMutableList()

    }





}







