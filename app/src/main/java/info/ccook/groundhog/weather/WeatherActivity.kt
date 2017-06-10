package info.ccook.groundhog.weather

import android.Manifest
import android.arch.lifecycle.*
import android.os.Bundle
import info.ccook.groundhog.BaseActivity
import info.ccook.groundhog.Emission
import info.ccook.groundhog.R
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class WeatherActivity : BaseActivity(), LifecycleRegistryOwner {

    companion object {
        val LOCATION_REQUEST_CODE = 100
    }

    private val lifecycleRegistry = LifecycleRegistry(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val weatherViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(WeatherViewModel::class.java)

        if (hasLocationPermission()) {
            weatherViewModel.currentLocation().observe(this, Observer {
                if (it?.data != null) {
                    weatherViewModel.getCurrentWeatherByCoordinatesOnce(it.data)
                }
            })

            weatherViewModel.currentWeather().observe(this, Observer {
                when(it?.status) {
                    Emission.Status.SUCCESS -> displayCurrentWeatherOrShowError(it.data)
                    Emission.Status.LOADING -> showCurrentWeatherLoading()
                    else -> showCurrentWeatherError()
                }
            })

            weatherViewModel.getCurrentLocationOnce()
        } else {
            requestLocationPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    private fun hasLocationPermission() = EasyPermissions
            .hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(this, getString(R.string.gpsRequestMessage),
                LOCATION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun displayCurrentWeatherOrShowError(weather: CurrentWeather.Data?) {
        when(weather) {
            null -> showCurrentWeatherError()
            else -> displayCurrentWeather(weather)
        }
    }

    private fun displayCurrentWeather(weather: CurrentWeather.Data) {
        locationName.text = weather.locationName
        currentTemp.text = weather.currentTemp
        high.text = weather.high
        low.text = weather.low
        humidity.text = weather.humidityPercentage
        windSpeed.text = weather.windSpeed
        windDirection.text = weather.windDirection
    }

    private fun showCurrentWeatherError() {

    }

    private fun showCurrentWeatherLoading() {

    }
}