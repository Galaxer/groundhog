package info.ccook.groundhog.config

import android.arch.lifecycle.LiveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import info.ccook.groundhog.Emission
import javax.inject.Inject

class AppConfig @Inject constructor(
        private val config: FirebaseRemoteConfig): LiveData<Emission<AppConfig.Data>>() {

    companion object {
        val ERROR_MESSAGE = "Error fetching config"
    }

    fun fetch() {
        postValue(Emission.loading())
        config.fetch().addOnCompleteListener {
            if (it.isSuccessful) {
                config.activateFetched()
                postValue(Emission.success(Data(config)))
            } else {
                postValue(Emission.error(ERROR_MESSAGE))
            }
        }
    }

    data class Data(private val config: FirebaseRemoteConfig) {
        val openWeatherMapApiKey: String = config.getString("openWeatherMapApiKey")
    }
}