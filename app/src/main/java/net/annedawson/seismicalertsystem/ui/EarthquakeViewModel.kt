package net.annedawson.seismicalertsystem.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import net.annedawson.seismicalertsystem.data.Earthquake
import net.annedawson.seismicalertsystem.data.getEarthquakes

class EarthquakeViewModel : ViewModel() {
    private val _earthquakes = MutableStateFlow<List<Earthquake>>(emptyList())
    val earthquakes: StateFlow<List<Earthquake>> = _earthquakes

    init {
        viewModelScope.launch {
            try {
                val result = getEarthquakes()
                _earthquakes.value = result
                Log.d("EarthquakeViewModel", "Fetched ${result.size} earthquakes")
            } catch (e: Exception) {
                Log.e("EarthquakeViewModel", "Error fetching earthquakes", e)
            }
        }
    }
}