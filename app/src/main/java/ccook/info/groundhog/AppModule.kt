package ccook.info.groundhog

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import ccook.info.groundhog.weather.CurrentLocation
import com.google.android.gms.location.LocationRequest
import dagger.Module
import dagger.Provides
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import javax.inject.Singleton

@Module(subcomponents = arrayOf(ViewModelSubComponent::class))
class AppModule constructor(private val application: Application) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideViewModelFactory(
            viewModelSubComponent: ViewModelSubComponent.Builder): ViewModelProvider.Factory {
        return ViewModelFactory(viewModelSubComponent.build())
    }

    @Singleton
    @Provides
    fun provideLocationProvider(context: Context): ReactiveLocationProvider =
            ReactiveLocationProvider(context)

    @Singleton
    @Provides
    fun provideLocationRequest(): LocationRequest =
            LocationRequest.create().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

    @Singleton
    @Provides
    fun provideCurrentLocation(locationProvider: ReactiveLocationProvider,
                               locationRequest: LocationRequest): CurrentLocation =
            CurrentLocation(locationProvider, locationRequest)
}