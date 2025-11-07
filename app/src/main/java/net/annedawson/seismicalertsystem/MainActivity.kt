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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.annedawson.seismicalertsystem.data.Earthquake
import net.annedawson.seismicalertsystem.ui.EarthquakeViewModel
import net.annedawson.seismicalertsystem.ui.theme.SeismicAlertSystemTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * The main activity of the application. This is the entry point of the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    // A by viewModels() delegate to create a ViewModel that is scoped to this activity.
    private val viewModel: EarthquakeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() allows the app to draw behind the system bars.
        enableEdgeToEdge()
        // setContent is the entry point for Composable content.
        setContent {
            // SeismicAlertSystemTheme is a custom theme for the app.
            SeismicAlertSystemTheme {
                // Scaffold is a layout that provides a framework for the app's UI.
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { EarthquakeTopAppBar() }
                ) { innerPadding ->
                    // EarthquakeScreen is a composable that displays the list of earthquakes.
                    EarthquakeScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

/**
 * A composable that displays the top app bar.
 * @param modifier A modifier to apply to the composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EarthquakeTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        modifier = modifier
    )
}

/**
 * A composable that displays the list of earthquakes.
 * @param modifier A modifier to apply to the composable.
 * @param viewModel The ViewModel that provides the earthquake data.
 */
@Composable
fun EarthquakeScreen(modifier: Modifier = Modifier, viewModel: EarthquakeViewModel) {
    // earthquakes is a State object that holds the list of earthquakes.
    // The collectAsState() function collects the flow from the ViewModel and converts it to a State object.
    val earthquakes by viewModel.earthquakes.collectAsState()

    // EarthquakeList is a composable that displays the list of earthquakes.
    EarthquakeList(earthquakes = earthquakes, modifier = modifier)
}

/**
 * A composable that displays a list of earthquakes.
 * @param earthquakes The list of earthquakes to display.
 * @param modifier A modifier to apply to the composable.
 */
@Composable
fun EarthquakeList(earthquakes: List<Earthquake>, modifier: Modifier = Modifier) {
    // LazyColumn is a vertically scrolling list that only composes and lays out the currently visible items.
    LazyColumn(modifier = modifier) {
        // items is a factory for creating items in a LazyColumn.
        items(earthquakes) { earthquake ->
            // EarthquakeItem is a composable that displays a single earthquake.
            EarthquakeItem(earthquake = earthquake)
        }
    }
}

/**
 * A composable that displays a single earthquake.
 * @param earthquake The earthquake to display.
 * @param modifier A modifier to apply to the composable.
 */
@Composable
fun EarthquakeItem(earthquake: Earthquake, modifier: Modifier = Modifier) {
    // Card is a container for content that provides a background and elevation.
    Card(modifier = modifier.padding(8.dp)) {
        // Column is a layout that arranges its children in a vertical sequence.
        Column(modifier = Modifier.padding(16.dp)) {
            // Text is a composable that displays text.
            Text(text = "Magnitude: ${earthquake.magnitude}")
            Text(text = "Location: ${earthquake.place}")
            Text(text = "Time: ${formatDate(earthquake.time)}")
        }
    }
}

/**
 * A function to format a timestamp into a human-readable date string.
 * @param timeInMillis The timestamp to format.
 * @return A formatted date string.
 */
private fun formatDate(timeInMillis: Long): String {
    // SimpleDateFormat is a class for formatting and parsing dates in a locale-sensitive manner.
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    // format() formats a Date into a date/time string.
    return dateFormat.format(Date(timeInMillis))
}

/**
 * A preview of the EarthquakeScreen composable.
 */
@Preview(showBackground = true)
@Composable
fun EarthquakeScreenPreview() {
    SeismicAlertSystemTheme {
        EarthquakeScreen(viewModel = EarthquakeViewModel())
    }
}
