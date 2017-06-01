package ccook.info.groundhog

import android.arch.lifecycle.ViewModelProvider
import ccook.info.groundhog.weather.WeatherModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, WeatherModule::class))
interface AppComponent {
    fun viewModelFactory(): ViewModelProvider.Factory

    fun inject(app: App)
}