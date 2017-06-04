package info.ccook.groundhog

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.ArrayMap
import info.ccook.groundhog.splash.SplashViewModel
import info.ccook.groundhog.weather.WeatherViewModel
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject
constructor(viewModelSubComponent: ViewModelSubComponent) : ViewModelProvider.Factory {

    private val creators: ArrayMap<Class<*>, Callable<out ViewModel>> = ArrayMap()

    init {
        creators.put(WeatherViewModel::class.java,
                Callable { viewModelSubComponent.weatherViewModel() })
        creators.put(SplashViewModel::class.java,
                Callable { viewModelSubComponent.splashViewModel() })
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator = creators[modelClass]

        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }

        if (creator == null) {
            throw IllegalArgumentException("unknown model class " + modelClass)
        }

        try {
            return creator.call() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}