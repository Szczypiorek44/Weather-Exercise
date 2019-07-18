package pl.developit.weatherexercise.presentation.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.domain.WeatherUseCases
import pl.developit.weatherexercise.presentation.utils.notifyChanged

class SearchViewModel(private val weatherUseCases: WeatherUseCases) : ViewModel() {

    val cityName = MutableLiveData<String>().apply { value = "" }
    val cityList = MutableLiveData<List<City>>().apply { value = ArrayList() }
    val cityHistory = MutableLiveData<MutableSet<String>>()
    val isRefreshing = MutableLiveData<Boolean>()

    private val event = LiveEvent<SearchEvent>()
    val liveEvent: LiveData<SearchEvent> = event

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun getCityHistory() {
        cityHistory.value = weatherUseCases.getCityHistory().toMutableSet()
    }

    fun findCities() {
        weatherUseCases.findCities(cityName.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isRefreshing.value = true }
            .doFinally { isRefreshing.value = false }
            .subscribe(
                {
                    cityList.value = it.first

                    cityHistory.value?.add(it.second)
                    cityHistory.notifyChanged()
                },
                {
                    cityList.value = ArrayList()
                    event.value = SearchEvent.Error(it.localizedMessage)
                }
            ).addTo(disposables)
    }

    sealed class SearchEvent {
        class Error(val error: String) : SearchEvent()
    }

}