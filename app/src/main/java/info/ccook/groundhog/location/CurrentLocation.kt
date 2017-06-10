package info.ccook.groundhog.location

import android.arch.lifecycle.LiveData
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import info.ccook.groundhog.Emission
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CurrentLocation @Inject constructor(
        private val locationProvider: RxLocation,
        private val locationRequest: LocationRequest) : LiveData<Emission<Location>>() {

    companion object {
        val ERROR_MESSAGE = "Error getting location"
    }

    private var disposable: Disposable? = null

    fun get() {
        postValue(Emission.loading())
        disposable = locationProvider.location().updates(locationRequest)
                .take(1)
                .subscribe({
                    postValue(Emission.success(it))
                }, {
                    postValue(Emission.error(ERROR_MESSAGE))
                })
    }

    override fun onInactive() {
        disposable?.dispose()
    }
}