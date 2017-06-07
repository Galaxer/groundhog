package info.ccook.groundhog.config

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import info.ccook.groundhog.Emission
import javax.inject.Inject

class AppConfig @Inject constructor(
        private val config: FirebaseRemoteConfig): LiveData<Emission<AppConfig.Data>>() {

    companion object {
        val errorMessage = "Error fetching config"
    }

    fun fetch() {
        postValue(Emission.loading())
        config.fetch().addOnCompleteListener { task: Task<Void> ->
            if (task.isSuccessful) {
                config.activateFetched()
                postValue(Emission.success(Data(config)))
            } else {
                postValue(Emission.error(errorMessage))
            }
        }
    }

    data class Data(private val config: FirebaseRemoteConfig) {
        val openWeatherMapApiKey: String = config.getString("openWeatherMapApiKey")
    }
}