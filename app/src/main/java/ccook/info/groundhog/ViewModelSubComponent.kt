package ccook.info.groundhog

import ccook.info.groundhog.weather.WeatherViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun weatherViewModel(): WeatherViewModel
}