package com.example.fittrack.ui.Screens.Ejercicios

import android.R
import android.annotation.SuppressLint
import android.graphics.Paint.Align
import android.text.Layout.Alignment
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.fittrack.Data.Entities.Ejercicio
import com.example.fittrack.Data.Entities.Rutina
import com.example.fittrack.ui.ViewModels.MainViewModel


@Composable
fun RutinaDialog(
    mainViewModel: MainViewModel
){
    Dialog(onDismissRequest = {mainViewModel.OpenCloseEjercicioForm(false)}) {

        var nombreRutina = mainViewModel.nombreRutina
        val listaDias = listOf(0,1,2,3,4,5,6)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp),
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
                        text = "Rutina",
                        fontSize = 20.sp,
                    )
                    OutlinedTextField(
                        value = nombreRutina,
                        onValueChange = { mainViewModel.nombreRutina = it},

                        label = ({ Text(text = "Nombre")}),

                        )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    color = Color.Gray,
                    thickness = 1.dp
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(15.dp)
                        .height(200.dp)
                ){
                    items(items = listaDias, key = {it}){ item ->
                        DiaForm(mainViewModel = mainViewModel, label = item)
                    }
                }



                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = { mainViewModel.OpenCloseRutinasForm(false) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                    Button(
                        onClick = { if (nombreRutina.length > 2) {
                            mainViewModel.insertRutina()
                            mainViewModel.OpenCloseRutinasForm(false)
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}


@Composable
fun DiaForm(
    mainViewModel: MainViewModel,
    label: Int
) {

    val DiaSemana: String
    //TODO Traducir
    when(label) {

        0 -> DiaSemana = "Lunes"
        1 -> DiaSemana = "Martes"
        2 -> DiaSemana = "Miercoles"
        3 -> DiaSemana = "Jueves"
        4 -> DiaSemana = "Viernes"
        5 -> DiaSemana = "Sabado"
        6 -> DiaSemana = "Domingo"

        else -> {
            DiaSemana = "error"
        }

    }


    Column (
        modifier = Modifier
            .padding(15.dp)
    ){
        Text(
            text = DiaSemana,
            fontSize = 20.sp,
        )
        DropdownEntrenamienotos(mainViewModel = mainViewModel, label = label)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownEntrenamienotos(
    mainViewModel: MainViewModel,
    label: Int
){
    var expanded by remember { mutableStateOf(false) }
    val entrenamientos by mainViewModel.lista_entrenamientos.collectAsState(initial = emptyList())


    var selectedEentrenamiento = mainViewModel.listEntrenamientos[label]



    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },

        ) {
        TextField(
            value = selectedEentrenamiento,
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
            entrenamientos.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.nombre) },
                    onClick = {
                        // selectedText = item
                        mainViewModel.actualizarDia(label, item.nombre)
                        expanded = false

                        //Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}



@Composable
fun ListaRutinas(
    mainViewModel: MainViewModel
){
    val rutinas by mainViewModel.rutinas.collectAsState(initial = emptyList())
    val rutinaActiva by mainViewModel.rutinaActiva.collectAsState(initial = Rutina("","",false))

    rutinaActiva?.let { Text(text = it.nombre) }
    LazyColumn(
        modifier = Modifier.padding(15.dp)
    ){
        items(items = rutinas, key = {it.nombre}){ item ->
            ItemRutina2(mainViewModel = mainViewModel, rutina = item)
        }
    }
}

@Composable
fun ItemRutina(
    mainViewModel: MainViewModel,
    rutina: Rutina
){
    Card (
        modifier = Modifier
            .padding(bottom = 7.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                mainViewModel.OpenCloseRutinasForm(true, rutina)
            })
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
                    text = rutina.nombre,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

            // Otro elemento en la fila que se alinea a la derecha
            Box() {
                Switch(
                    checked = rutina.activa,
                    onCheckedChange = {
                        rutina.activa = true

                    },
                    modifier = Modifier.padding(15.dp)

                )
                IconButton(
                    onClick = {
                        mainViewModel.deleteRutina(rutina)
                        mainViewModel.OpenCloseRutinasForm(false)
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
fun ItemRutina2(
    mainViewModel: MainViewModel,
    rutina: Rutina
){

    var sChecked by remember {
        mutableStateOf(rutina.activa)
    }

    Card (
        modifier = Modifier
            .padding(bottom = 7.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                mainViewModel.OpenCloseRutinasForm(true, rutina)
            })
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            Text(
                text = rutina.nombre,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Switch(
                    checked = sChecked,
                    onCheckedChange = {
                        sChecked = !sChecked
                        rutina.activa = sChecked
                        mainViewModel.activarRutina(rutina)
                    },
                    modifier = Modifier
                        .size(25.dp)
                        .padding(top = 20.dp)

                )
            }


            IconButton(
                onClick = {
                    mainViewModel.deleteRutina(rutina)
                    mainViewModel.OpenCloseRutinasForm(false)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .padding(4.dp)
                )
            }
        }
    }
}







