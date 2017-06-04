package info.ccook.groundhog.location

import android.arch.lifecycle.LiveData
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CurrentLocation @Inject constructor(
        private val locationProvider: RxLocation,
        private val locationRequest: LocationRequest) : LiveData<Location>() {

    private lateinit var disposable: Disposable

    override fun onActive() {
        disposable = locationProvider.location().updates(locationRequest)
                .take(1)
                .subscribe { updatedLocation ->
                    postValue(updatedLocation)
                }
    }

    override fun onInactive() {
        disposable.dispose()
    }
}