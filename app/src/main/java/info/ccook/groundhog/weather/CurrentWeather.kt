package info.ccook.groundhog.weather

import android.arch.lifecycle.LiveData
import android.location.Location
import com.visuality.f32.temperature.TemperatureUnit
import com.visuality.f32.weather.data.entity.Weather
import com.visuality.f32.weather.manager.WeatherManager
import info.ccook.groundhog.Emission
import info.ccook.groundhog.config.AppConfig
import javax.inject.Inject

class CurrentWeather @Inject constructor(
        appConfig: AppConfig) : LiveData<Emission<CurrentWeather.Data>>() {

    companion object {
        val ERROR_MESSAGE = "Error getting current weather by coordinates"
    }

    private val weatherManager = WeatherManager(appConfig.value?.data?.openWeatherMapApiKey)

    fun getByCoordinates(location: Location) {
        postValue(Emission.loading())
        weatherManager.getCurrentWeatherByCoordinates(location.latitude, location.longitude,
                object : WeatherManager.CurrentWeatherHandler {
                    override fun onFailedToReceiveCurrentWeather(manager: WeatherManager) {
                        postValue(Emission.error(ERROR_MESSAGE))
                    }

                    override fun onReceivedCurrentWeather(manager: WeatherManager,
                                                          weather: Weather) {
                        postValue(Emission.success(Data(weather)))
                    }
                })
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