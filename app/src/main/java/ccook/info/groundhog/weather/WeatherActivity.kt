package ccook.info.groundhog.weather

import android.Manifest
import android.arch.lifecycle.*
import android.os.Bundle
import ccook.info.groundhog.BaseActivity
import ccook.info.groundhog.R
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
            weatherViewModel.getLocation().observe(this, Observer { location ->
                this.latitude.text = location?.latitude.toString()
                this.longitude.text = location?.longitude.toString()
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