package com.example.fittrack.ui.Screens

import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fittrack.R
import com.example.fittrack.ui.DataStore.Language
import com.example.fittrack.ui.ViewModels.SettingsViewModel
import java.util.Objects.toString

@Composable
fun ScreenSettings(
     navController: NavController,
     settingsViewModel: SettingsViewModel
) {

    MyComposeLayout(settingsViewModel)




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrpodownIdiomas(
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current
    val idiomas = arrayOf("English", "Spanish")
    var expanded by remember { mutableStateOf(false) }
    val selectedText by settingsViewModel.lang.collectAsState(initial = Language.Spanish)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .width(30.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText.lName,
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
                idiomas.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            // selectedText = item
                            expanded = false
                            when(item){

                                Language.English.lName -> settingsViewModel.updateLanguage(Language.English)
                                Language.Spanish.lName -> settingsViewModel.updateLanguage(Language.Spanish)

                            }
                            //Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun MyComposeLayout(settingsViewModel: SettingsViewModel) {

    val sChecked = settingsViewModel.theme.collectAsState(initial = false)
    val lang by settingsViewModel.lang.collectAsState(initial = Language.Spanish)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Texto junto con un Switch en la misma fila
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.darkMode),
                style = MaterialTheme.typography.headlineSmall
            )
            Switch(
                checked = sChecked.value,
                onCheckedChange = {
                    settingsViewModel.updateTheme(it)
                },
                modifier = Modifier.padding(15.dp)

            )
        }

        // Línea que delimita los elementos
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.language),
                style = MaterialTheme.typography.headlineSmall
            )
            DrpodownIdiomas(settingsViewModel)


        }



    }
}


