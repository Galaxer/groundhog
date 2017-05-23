package ccook.info.groundhog

import android.arch.lifecycle.ViewModelProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun viewModelFactory(): ViewModelProvider.Factory
}