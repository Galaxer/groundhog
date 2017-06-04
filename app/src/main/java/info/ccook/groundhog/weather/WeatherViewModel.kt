package info.ccook.groundhog.weather

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import info.ccook.groundhog.location.CurrentLocation
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
        private val currentLocation: CurrentLocation,
        private val currentWeather: CurrentWeather) : ViewModel() {

    fun getCurrentWeather(): LiveData<CurrentWeather.Data> {
        return Transformations.switchMap(currentLocation, currentWeather::getByCoordinates)
    }
}