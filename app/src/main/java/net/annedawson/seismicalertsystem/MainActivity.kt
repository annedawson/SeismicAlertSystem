package net.annedawson.seismicalertsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.annedawson.seismicalertsystem.data.Earthquake
import net.annedawson.seismicalertsystem.ui.EarthquakeViewModel
import net.annedawson.seismicalertsystem.ui.theme.SeismicAlertSystemTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val viewModel: EarthquakeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeismicAlertSystemTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EarthquakeScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun EarthquakeScreen(modifier: Modifier = Modifier, viewModel: EarthquakeViewModel) {
    val earthquakes by viewModel.earthquakes.collectAsState()

    EarthquakeList(earthquakes = earthquakes, modifier = modifier)
}

@Composable
fun EarthquakeList(earthquakes: List<Earthquake>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(earthquakes) { earthquake ->
            EarthquakeItem(earthquake = earthquake)
        }
    }
}

@Composable
fun EarthquakeItem(earthquake: Earthquake, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Magnitude: ${earthquake.magnitude}")
            Text(text = "Location: ${earthquake.place}")
            Text(text = "Time: ${formatDate(earthquake.time)}")
        }
    }
}

private fun formatDate(timeInMillis: Long): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(timeInMillis))
}

@Preview(showBackground = true)
@Composable
fun EarthquakeScreenPreview() {
    SeismicAlertSystemTheme {
        EarthquakeScreen(viewModel = EarthquakeViewModel())
    }
}
