package ccook.info.groundhog.weather

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(WeatherSubComponent::class))
abstract class WeatherModule {

    @Binds
    @IntoMap
    @ActivityKey(WeatherActivity::class)
    internal abstract fun bindYourActivityInjectorFactory(builder: WeatherSubComponent.Builder):
            AndroidInjector.Factory<out Activity>
}