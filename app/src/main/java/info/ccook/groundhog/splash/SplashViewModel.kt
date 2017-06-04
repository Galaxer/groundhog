package info.ccook.groundhog.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import info.ccook.groundhog.AppConfig
import javax.inject.Inject

class SplashViewModel @Inject constructor(private var appConfig: AppConfig) : ViewModel() {

    fun fetchConfig(): LiveData<AppConfig.Data> {
        return appConfig
    }
}