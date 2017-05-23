package ccook.info.groundhog

import android.app.Application

class App : Application() {

    val component: AppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

}