package info.ccook.groundhog.weather

import android.Manifest
import android.arch.lifecycle.*
import android.os.Bundle
import info.ccook.groundhog.BaseActivity
import info.ccook.groundhog.R
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class WeatherActivity : BaseActivity(), LifecycleRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val weatherViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(WeatherViewModel::class.java)

        if (hasFineLocationPermission()) {
            weatherViewModel.getCurrentWeather().observe(this, Observer { weather ->
                locationName.text = weather?.locationName
                currentTemp.text = weather?.currentTemp
                high.text = weather?.high
                low.text = weather?.low
                humidity.text = weather?.humidityPercentage
                windSpeed.text = weather?.windSpeed
                windDirection.text = weather?.windDirection
            })
        } else {
            requestFineLocationPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    private fun hasFineLocationPermission() = EasyPermissions
            .hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestFineLocationPermission() {
        EasyPermissions.requestPermissions(this, getString(R.string.gps_request_message), 100,
                Manifest.permission.ACCESS_FINE_LOCATION)
    }
}