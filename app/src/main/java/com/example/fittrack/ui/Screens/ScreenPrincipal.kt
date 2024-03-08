import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fittrack.ui.DateUtils
import com.example.fittrack.ui.Navigation.NavItem
import java.text.DateFormat
import java.util.Calendar


@Composable
fun ScreenPrincipal(
    navController: NavController
) {
    PrincipalBodyContent( navController)

}



@Composable
fun PrincipalBodyContent(
    navController: NavController
) {
    Column {
        ManufacturedDate()
        DailyGoalsList()
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