package info.ccook.groundhog.splash

import android.arch.lifecycle.ViewModel
import info.ccook.groundhog.config.AppConfig
import javax.inject.Inject

class SplashViewModel @Inject constructor(private var appConfig: AppConfig) : ViewModel() {

    fun appConfig() = appConfig

    fun fetchConfigOnce() {
       when(appConfig.value) {
           null -> fetchConfig()
       }
    }

    fun fetchConfig() = appConfig.fetch()
}