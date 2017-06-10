package info.ccook.groundhog.weather

import android.arch.lifecycle.ViewModel
import android.location.Location
import info.ccook.groundhog.location.CurrentLocation
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
        private val currentLocation: CurrentLocation,
        private val currentWeather: CurrentWeather) : ViewModel() {

    fun currentWeather() = currentWeather

    fun currentLocation() = currentLocation

    fun getCurrentLocationOnce() {
        when(currentWeather.value) {
            null -> currentLocation().get()
        }
    }

    fun getCurrentWeatherByCoordinatesOnce(location: Location) {
        when(currentWeather.value) {
            null -> currentWeather.getByCoordinates(location)
        }
    }
}