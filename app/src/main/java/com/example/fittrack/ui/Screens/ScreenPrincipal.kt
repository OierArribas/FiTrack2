import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavController
import com.example.fittrack.Data.Entities.Entrenamiento
import com.example.fittrack.Data.Entities.Rutina
//import com.example.fittrack.Manifest
import com.example.fittrack.R
import com.example.fittrack.WaterNotificationService
import com.example.fittrack.ui.DateUtils
import com.example.fittrack.ui.Navigation.NavItem
import com.example.fittrack.ui.Screens.Ejercicios.DiaForm
import com.example.fittrack.ui.Screens.Ejercicios.ItemEntrenaminento
import com.example.fittrack.ui.ViewModels.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
//import com.google.accompanist.permissions.rememberPermissionState
import java.text.DateFormat
import java.util.Calendar
import kotlin.random.Random


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ScreenPrincipal(
    navController: NavController,
    mainViewModel: MainViewModel
) {




    Column {
        ManufacturedDate()
        if (mainViewModel.visualizarEntrenamientoDiario) {
            DialogoEntrenamiento(mainViewModel = mainViewModel)
        } else {
            DailySplit(mainViewModel = mainViewModel)
        }

    }


}


@Composable
fun ManufacturedDate() {
    val calendar = Calendar.getInstance().time
    val dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(calendar)
    val gradientColors = listOf(Color.Red, Color.Magenta,Color.Blue,   /*...*/)
    val gradientColors2 = listOf(Color.Blue,Color.Cyan, Color.Green /*...*/)

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = dateFormat,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(15.dp),
            color = Color.DarkGray
            /*style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )
            */

        )
    }

}


@Composable
fun DailyGoalsList() {

    Column() {
        DailyGoal(entrenamieto = "Pecho Espalda", tipo = "Tipo: Entrenamiento de Fuerza" )
    }
}

@Preview(showBackground = false)
@Composable
fun DailyGoal(
    entrenamieto: String = "Fuerza",
    tipo: String = "Tipo: Fuerza"
) {
    Card (

        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable(onClick = {}),
    ){
        Column (

            modifier = Modifier
                .padding(15.dp)

        ){
            Column (
                modifier = Modifier
                    .padding(end = 7.dp)
                    .fillMaxWidth()

                ,
            ){
                Text(text = entrenamieto, fontWeight = FontWeight.Bold, fontSize = 25.sp, modifier = Modifier.height(40.dp))
                Text(text = tipo, fontSize = 20.sp,
                    modifier = Modifier
                    .fillMaxWidth()
                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row (
                modifier = Modifier
                    .padding(end = 7.dp)
                    .fillMaxWidth()
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Completado")
                }
                CircularProgressIndicator(
                    progress = 0.75f,
                    modifier = Modifier
                        .padding(start = 100.dp, top = 3.dp))
                Text(text = "75%",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 10.dp))

            }
        }
    }
}
// Fuerza
// Cardio


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun DailySplit(
    mainViewModel: MainViewModel
) {
    //val rutinaActual = mainViewModel.rutinaActiva.collectAsState(initial = Rutina("","",false))


    val rutinaActiva by mainViewModel.rutinaActiva.collectAsState(initial = Rutina("","",false))

    rutinaActiva?.let {

        val splitActivo = mainViewModel.stringEntRutALista(it.entrenamientos)
        var nombreEntrenamiento = ""

        val calendar = Calendar.getInstance()
        val diaDeLaSemana = calendar.get(Calendar.DAY_OF_WEEK)

        val nombreDia = when (diaDeLaSemana) {
            Calendar.SUNDAY -> 6
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            else -> "Desconocido"
        }

        var contador = 0
        for( i in splitActivo) {
            if (contador == nombreDia) {
                nombreEntrenamiento = i
            }
            contador += 1
        }

        Entr(mainViewModel = mainViewModel, entrenamieto = nombreEntrenamiento)





    }


}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Entr(
    mainViewModel: MainViewModel,
    entrenamieto: String
){
    val entrenamientos by mainViewModel.lista_entrenamientos.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.padding(15.dp)
    ){
        items(items = entrenamientos, key = {it.nombre}){ item ->
            if (item.nombre == entrenamieto){
                VisualizacionEntrenamiento(mainViewModel = mainViewModel, entrenamiento = item)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VisualizacionEntrenamiento(
    mainViewModel: MainViewModel,
    entrenamiento: Entrenamiento
){

    val context = LocalContext.current
    val postNotificationPermission=
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val waterNotificationService=WaterNotificationService(context)


    Card (
        modifier = Modifier
            .padding(bottom = 7.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                waterNotificationService.showBasicNotification()
                mainViewModel.OpenCloseVisualizacionEntrenamientoDiario(true, entrenamiento)

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
                    .weight(1f)
            ) {
                Text(
                    text = entrenamiento.nombre,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

        }
    }
}
@Composable
fun DialogoEntrenamiento(
    mainViewModel: MainViewModel,

) {
    val nombreEntrenamiento = mainViewModel.nombreEntrenamientoVisualizado
    val listaEjercicios = mainViewModel.listaEjerciciosEntrenamientoVisualizado

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Título y botón de minimizar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // Espacio entre los elementos
                verticalAlignment = Alignment.CenterVertically // Alineación vertical al centro
            ) {
                // Título del entrenamiento
                Text(
                    text = nombreEntrenamiento,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(1f) // Toma el máximo ancho disponible
                )
                // Botón de minimizar
                IconButton(
                    onClick = { mainViewModel.OpenCloseVisualizacionEntrenamientoDiario(false) } // Llama a la función proporcionada para minimizar el diálogo
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Minimize",
                        tint = Color.Black // Cambia el color del icono según sea necesario
                    )
                }
            }

            // Divider
            Divider(color = Color.Gray, thickness = 1.dp)

            // Lista de ejercicios
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .background(Color.White)
                    .align(Alignment.CenterHorizontally)
            ) {
                listaEjercicios.forEach { ejercicio ->
                    Text(
                        text = ejercicio,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

fun notificar(context: Context) {

    val notificationManager=context.getSystemService(NotificationManager::class.java)

    val notification=NotificationCompat.Builder(context,"water_notification")
        .setContentTitle("Water Reminder")
        .setContentText("Time to drink a glass of water")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationManager.IMPORTANCE_HIGH)
        .setAutoCancel(true)


    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        notify(0, notification.build())
    }


}
