package com.example.fittrack.ui.Screens.Ejercicios

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.fittrack.Data.Entities.Ejercicio
import com.example.fittrack.Data.Entities.Entrenamiento
import com.example.fittrack.ui.ViewModels.MainViewModel
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.fittrack.R
import com.example.fittrack.ui.DataStore.Language

@Composable
fun EntrenamientoDialog(
    mainViewModel: MainViewModel
){
    Dialog(onDismissRequest = {mainViewModel.OpenCloseEjercicioForm(false)}) {
        // Draw a rectangle shape with rounded corners inside the dialog

        var nombreEntrenamiento = mainViewModel.nombreEntrenamiento
        var series by remember { mutableStateOf("") }
        var selectedEjercicio = mainViewModel.selectedEjercicioEntrenamiento
        val listaEjercicios = mainViewModel.listEjercicios
        val cadenaListaejercicios = mainViewModel.listaEjercicios


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                //.padding(16.dp)
                    ,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,

                ) {
                Column (
                    modifier = Modifier
                        .padding(15.dp)
                ){
                    Text(
                        text = stringResource(id = R.string.training),
                        fontSize = 20.sp,
                    )
                    OutlinedTextField(
                        value = nombreEntrenamiento,
                        onValueChange = { mainViewModel.cambiarNombreEntrenamiento(it)},

                        label = ({ Text(text = stringResource(id = R.string.name)) }),
                    )


                    Column (
                        modifier = Modifier.padding( top = 10.dp)

                    ){



                        Text(
                            text = stringResource(id = R.string.exercises),
                            fontSize = 20.sp,
                        )

                        DropdownEjercicios(mainViewModel = mainViewModel)

                        Row (){

                            OutlinedTextField(
                                value = series,
                                onValueChange = {

                                    if (it.isEmpty() || (it.length == 1 && it.toIntOrNull() in 1..9) ){
                                        series = it
                                    }
                                }
                                    ,
                                label = ({ Text(text = stringResource(id = R.string.sets)) }),
                                modifier = Modifier.width(100.dp)
                                    //.height(40.dp)
                            )

                            Button(
                                onClick = { mainViewModel.AddEjercicio(selectedEjercicio, series)},
                                modifier = Modifier.padding(8.dp),
                            ) {
                                Text(stringResource(id = R.string.add))
                            }
                        }

                        ListaEjerciciosForm(mainViewModel = mainViewModel, ejercicios = listaEjercicios)

                    }


                    }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = { mainViewModel.OpenCloseEntrenamientosForm(false) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.dimiss))
                    }
                    Button(
                        onClick = { if (nombreEntrenamiento.length > 2){
                            mainViewModel.insertEntrenamiento()
                            mainViewModel.OpenCloseEntrenamientosForm(false)
                        }


                                  },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(stringResource(id = R.string.confirm))
                    }
                }



                }






            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownEjercicios(
    mainViewModel: MainViewModel
){
    var expanded by remember { mutableStateOf(false) }
    val ejercicios by mainViewModel.ejercicios.collectAsState(initial = emptyList())

    var selectedEjercicio = mainViewModel.selectedEjercicioEntrenamiento



    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },

    ) {
        TextField(
            value = selectedEjercicio,
            //value = "ladfhj",
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ejercicios.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.nombre) },
                    onClick = {
                        // selectedText = item
                        mainViewModel.changeSelectedEE(item.nombre)
                        expanded = false

                        //Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun ListaEjerciciosForm(
    mainViewModel: MainViewModel,
    ejercicios: List<String>
){
    //val ejercicios = mainViewModel.listEjercicios

    LazyColumn(
        modifier = Modifier.padding(15.dp)
            .height(100.dp)
    ){
        items(items = ejercicios, key = {it}){ item ->
            SubItemEjercico(mainViewModel = mainViewModel, ejercicio = item)
        }
    }
}


@Composable
fun ListaEntrenamientos(
    mainViewModel: MainViewModel
){
    val entrenamientos by mainViewModel.lista_entrenamientos.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.padding(15.dp)

    ){
        items(items = entrenamientos, key = {it.nombre}){ item ->
            ItemEntrenaminento(mainViewModel = mainViewModel, entrenamiento = item)
        }
    }
}

@Composable
fun ItemEntrenaminento(
    mainViewModel: MainViewModel,
    entrenamiento: Entrenamiento
){
    Card (
        modifier = Modifier
            .padding(bottom = 7.dp)
            .fillMaxWidth()
            .clickable(onClick = { mainViewModel.OpenCloseEntrenamientosForm(true, entrenamiento)})

    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Box(
                modifier = Modifier
                    .weight(1f) // Toma el espacio restante
            ) {
                Text(
                    text = entrenamiento.nombre,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

            // Otro elemento en la fila que se alinea a la derecha
            Box() {
                IconButton(
                    onClick = {
                        mainViewModel.deleteEntrenamiento(entrenamiento)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(MaterialTheme.shapes.small)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SubItemEjercico(
    mainViewModel: MainViewModel,
    ejercicio: String
){
    Card (
        modifier = Modifier
            .padding(bottom = 7.dp)
            .fillMaxWidth()
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Box(
                modifier = Modifier
                    .weight(1f) // Toma el espacio restante
            ) {
                Text(
                    text = ejercicio,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

            // Otro elemento en la fila que se alinea a la derecha
            Box() {
                IconButton(
                    onClick = {
                        mainViewModel.borrarSubEjercicio(ejercicio)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(MaterialTheme.shapes.small)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

