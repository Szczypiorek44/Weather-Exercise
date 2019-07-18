package pl.developit.weatherexercise.domain

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import io.reactivex.Single
import pl.developit.weatherexercise.R
import pl.developit.weatherexercise.data.api.WeatherApi
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.data.models.Conditions
import pl.developit.weatherexercise.presentation.utils.stringSet

class WeatherInteractor(
    private val context: Context,
    private val sharedPrefs: SharedPreferences,
    private val weatherApi: WeatherApi
) : WeatherUseCases {


    private var SharedPreferences.cityHistory by sharedPrefs.stringSet()

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun getCityHistory(): Set<String> {
        return sharedPrefs.cityHistory
    }

    override fun findCities(cityName: String?): Single<Pair<List<City>, String>> {
        val regex = Regex("[a-zA-Z]+")

        return when {
            cityName.isNullOrBlank() -> Single.error(Exception(context.getString(R.string.enter_city_name)))
            cityName.matches(regex).not() -> Single.error(Exception(context.getString(R.string.city_name_is_incorrect)))
            else -> weatherApi.findCities(getApiKey(), cityName)
                .map { it to cityName }
                .doOnSuccess { cityName.addToHistory() }
        }
    }


    override fun getConditions(cityKey: Long?): Single<Conditions> {
        return when (cityKey) {
            null -> Single.error(Exception(context.getString(R.string.unexpected_error_occurred)))
            else -> weatherApi.getCurrentConditions(cityKey, getApiKey()).map { it[0] }
        }
    }

    private fun String.addToHistory() {
        val cityHistory = sharedPrefs.cityHistory
        cityHistory?.add(this)
        sharedPrefs.cityHistory = cityHistory
    }

    private fun getApiKey(): String {
        val hash = stringFromJNI()
        val byteArray = Base64.decode(hash, Base64.DEFAULT)
        return String(byteArray)
    }

    private external fun stringFromJNI(): String
}

