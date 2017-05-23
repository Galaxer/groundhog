package ccook.info.groundhog.weather

import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.google.android.gms.location.LocationRequest
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import rx.Subscription
import javax.inject.Inject

class CurrentLocation @Inject constructor(
        private val locationProvider: ReactiveLocationProvider,
        private val locationRequest: LocationRequest) : MutableLiveData<Location>() {

    lateinit var subscription: Subscription

    override fun onActive() {
        subscription = locationProvider.getUpdatedLocation(locationRequest)
                .take(1)
                .subscribe { updatedLocation ->
                    postValue(updatedLocation)
                }
    }

    override fun onInactive() {
        subscription.unsubscribe()
    }
}