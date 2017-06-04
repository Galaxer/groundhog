package info.ccook.groundhog

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject

class AppConfig @Inject constructor(
        private val config: FirebaseRemoteConfig): LiveData<AppConfig.Data>() {

    override fun onActive() {
        config.fetch().addOnCompleteListener { task: Task<Void> ->
            if (task.isSuccessful) {
                config.activateFetched()
                postValue(Data(config))
            }
        }
    }

    data class Data constructor(private val config: FirebaseRemoteConfig) {
        val openWeatherMapApiKey: String = config.getString("openWeatherMapApiKey")
    }
}