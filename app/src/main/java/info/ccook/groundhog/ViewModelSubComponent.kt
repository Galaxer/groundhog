package info.ccook.groundhog

import dagger.Subcomponent
import info.ccook.groundhog.splash.SplashViewModel
import info.ccook.groundhog.weather.WeatherViewModel

@Subcomponent
interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun weatherViewModel(): WeatherViewModel
    fun splashViewModel(): SplashViewModel
}