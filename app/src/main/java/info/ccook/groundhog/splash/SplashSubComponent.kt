package info.ccook.groundhog.splash

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface SplashSubComponent : AndroidInjector<SplashActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SplashActivity>()
}