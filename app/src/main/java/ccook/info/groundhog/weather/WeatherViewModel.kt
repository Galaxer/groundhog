package ccook.info.groundhog.weather

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.location.Location
import ccook.info.groundhog.location.CurrentLocation
import javax.inject.Inject

class WeatherViewModel @Inject
constructor(private var currentLocation: CurrentLocation) : ViewModel() {

    fun getLocation(): LiveData<Location> {
        return currentLocation
    }
}