package info.ccook.groundhog

import android.arch.lifecycle.ViewModelProvider
import dagger.Component
import info.ccook.groundhog.splash.SplashModule
import info.ccook.groundhog.weather.WeatherModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, WeatherModule::class, SplashModule::class))
interface AppComponent {
    fun viewModelFactory(): ViewModelProvider.Factory

    fun inject(app: App)
}