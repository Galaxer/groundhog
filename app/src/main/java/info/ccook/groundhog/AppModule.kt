package info.ccook.groundhog

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.patloew.rxlocation.RxLocation
import dagger.Module
import dagger.Provides
import info.ccook.groundhog.location.CurrentLocation
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
    fun provideLocationProvider(context: Context): RxLocation = RxLocation(context)

    @Singleton
    @Provides
    fun provideLocationRequest(): LocationRequest =
            LocationRequest.create().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

    @Singleton
    @Provides
    fun provideCurrentLocation(locationProvider: RxLocation,
                               locationRequest: LocationRequest): CurrentLocation =
            CurrentLocation(locationProvider, locationRequest)

    @Singleton
    @Provides
    fun provideAppConfig(firebaseRemoteConfig: FirebaseRemoteConfig): AppConfig =
            AppConfig(firebaseRemoteConfig)

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig.setDefaults(R.xml.firebase_defaults)
        return firebaseRemoteConfig
    }
}