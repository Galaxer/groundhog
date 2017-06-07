package info.ccook.groundhog.weather

import android.arch.lifecycle.LiveData
import android.location.Location
import com.visuality.f32.temperature.TemperatureUnit
import com.visuality.f32.weather.data.entity.Weather
import com.visuality.f32.weather.manager.WeatherManager
import info.ccook.groundhog.config.AppConfig
import javax.inject.Inject

class CurrentWeather @Inject constructor(appConfig: AppConfig) : LiveData<CurrentWeather.Data>() {

    private val weatherManager = WeatherManager(appConfig.value?.data?.openWeatherMapApiKey)

    fun getByCoordinates(location: Location?): LiveData<Data> {
        if (location != null) {
            weatherManager.getCurrentWeatherByCoordinates(location.latitude, location.longitude,
                    object : WeatherManager.CurrentWeatherHandler {
                        override fun onReceivedCurrentWeather(manager: WeatherManager,
                                                              weather: Weather) {
                            postValue(Data(weather))
                        }

                        override fun onFailedToReceiveCurrentWeather(manager: WeatherManager) {}
                    })
        }
        return this
    }

    data class Data(private val weather: Weather) {

        val currentTemp = Math.round(weather.temperature.current
                .getValue(TemperatureUnit.FAHRENHEIT)).toString()

        val high = Math.round(weather.temperature.maximum
                .getValue(TemperatureUnit.FAHRENHEIT)).toString()

        val low = Math.round(weather.temperature.minimum
                .getValue(TemperatureUnit.FAHRENHEIT)).toString()

        val locationName: String = weather.navigation.locationName

        val humidityPercentage = weather.atmosphere.humidityPercentage.toString()

        val windSpeed = weather.wind.speed.toString()

        val windDirection = weather.wind.direction.toString()
    }
}