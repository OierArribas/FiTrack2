package com.example.fittrack.ui.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fittrack.R
import com.example.fittrack.Data.Entities.Ejercicio
import com.example.fittrack.ui.Screens.Ejercicios.EjercicoDialog
import com.example.fittrack.ui.Screens.Ejercicios.ListaEjercicios
import com.example.fittrack.ui.ViewModels.MainViewModel

@Composable
fun ScreenEntrenamientos(
    navController: NavController,
    mainViewModel: MainViewModel
) {


    if (mainViewModel.ejercicioForm){
        EjercicoDialog(mainViewModel = mainViewModel)
    }

    SecondBodyContent(navController, mainViewModel)
    FloatingButton(onClick = {
        mainViewModel.OpenCloseEjercicioForm(true)
    //mainViewModel.addEjercicio(Ejercicio(nombre = "Press Banca"))
    })
}


@Composable
fun SecondBodyContent(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    Column {
        EntrenamientosDropDownMenu()
        ListaEjercicios(mainViewModel = mainViewModel)
    }


}
@Preview
@Composable
fun EntrenamientosDropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var pagina by remember { mutableStateOf("Entrenamientos") }

    Row (modifier = Modifier
        .padding(15.dp),) {
        Text(
            text = stringResource(id = R.string.trainings),
            fontSize = 30.sp,

            color = Color.DarkGray
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.CenterEnd)
        ) {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }



        }
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentSize(Alignment.BottomEnd)
            .padding(15.dp)
    ) {
        FloatingActionButton(
            onClick = { onClick() },
            shape = CircleShape,

            ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }


}
