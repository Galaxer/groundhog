package info.ccook.groundhog.splash

import android.arch.lifecycle.*
import android.content.Intent
import android.os.Bundle
import android.view.View
import info.ccook.groundhog.BaseActivity
import info.ccook.groundhog.Emission
import info.ccook.groundhog.R
import info.ccook.groundhog.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : BaseActivity(), LifecycleRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SplashViewModel::class.java)

        retryButton.setOnClickListener {
            splashViewModel.fetchConfig()
        }

        splashViewModel.appConfig().observe(this, Observer { emission ->
            when(emission?.status) {
                Emission.Status.SUCCESS -> showWeather()
                Emission.Status.ERROR -> hideLoadingShowError()
                Emission.Status.LOADING -> hideErrorShowLoading()
            }
        })

        splashViewModel.fetchConfigOnce()
    }

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    fun showWeather() {
        val intent = Intent(this, WeatherActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    fun hideError() {
        errorContainer.visibility = View.GONE
    }

    fun showError() {
        errorContainer.visibility = View.VISIBLE
    }

    fun hideLoadingShowError() {
        hideLoading()
        showError()
    }

    fun hideErrorShowLoading() {
        hideError()
        showLoading()
    }
}