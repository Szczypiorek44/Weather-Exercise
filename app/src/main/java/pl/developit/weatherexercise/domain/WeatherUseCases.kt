package pl.developit.weatherexercise.domain

import io.reactivex.Single
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.data.models.Conditions

interface WeatherUseCases {
    fun getCityHistory(): Set<String>
    fun findCities(cityName: String?): Single<Pair<List<City>, String>>
    fun getConditions(cityKey: Long?): Single<Conditions>

}