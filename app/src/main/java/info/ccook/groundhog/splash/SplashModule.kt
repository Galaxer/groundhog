package info.ccook.groundhog.splash

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = arrayOf(SplashSubComponent::class))
abstract class SplashModule {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity::class)
    internal abstract fun bindYourActivityInjectorFactory(builder: SplashSubComponent.Builder):
            AndroidInjector.Factory<out Activity>
}