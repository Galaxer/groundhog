package ccook.info.groundhog.weather

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.google.android.gms.location.LocationRequest
import pl.charmas.android.reactivelocation.ReactiveLocationProvider

class WeatherViewModel constructor(application: Application) : AndroidViewModel(application) {

    private var location: MutableLiveData<Location> = MutableLiveData()

    fun getLocation(): LiveData<Location> {
        ReactiveLocationProvider(getApplication())
                .getUpdatedLocation(LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY))
                .take(1)
                .subscribe { updatedLocation ->
                    location.postValue(updatedLocation)
                }
        return location
    }
}