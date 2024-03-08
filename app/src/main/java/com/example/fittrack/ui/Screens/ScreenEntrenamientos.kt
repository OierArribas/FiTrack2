package com.example.fittrack.ui.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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

@Composable
fun ScreenEntrenamientos(
    navController: NavController,
    string: String?
) {
    SecondBodyContent(navController)
}


@Composable
fun SecondBodyContent(
    navController: NavController
) {
    EntrenamientosDropDownMenu()
    FloatingButton(onClick = {})
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