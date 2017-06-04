package info.ccook.groundhog.splash

import android.arch.lifecycle.*
import android.content.Intent
import android.os.Bundle
import info.ccook.groundhog.BaseActivity
import info.ccook.groundhog.R
import info.ccook.groundhog.weather.WeatherActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), LifecycleRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val splashViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SplashViewModel::class.java)

        splashViewModel.fetchConfig().observe(this, Observer { _ ->
            showWeather()
        })
    }

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    fun showWeather() {
        val intent = Intent(this, WeatherActivity::class.java)
        startActivity(intent)
        finish()
    }
}