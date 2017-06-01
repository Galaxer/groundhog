package ccook.info.groundhog.weather

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface WeatherSubComponent : AndroidInjector<WeatherActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherActivity>()
}