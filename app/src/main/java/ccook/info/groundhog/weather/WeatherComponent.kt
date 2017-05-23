package ccook.info.groundhog.weather

import ccook.info.groundhog.AppComponent
import ccook.info.groundhog.PerActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class))
interface WeatherComponent {
    fun inject(weatherActivity: WeatherActivity)
}