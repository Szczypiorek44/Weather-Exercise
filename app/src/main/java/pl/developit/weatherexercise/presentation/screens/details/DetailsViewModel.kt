package pl.developit.weatherexercise.presentation.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import pl.developit.weatherexercise.R
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.data.models.Conditions
import pl.developit.weatherexercise.domain.WeatherUseCases

class DetailsViewModel(private val weatherUseCases: WeatherUseCases) : ViewModel() {

    val city = MutableLiveData<City>()
    val conditions = MutableLiveData<Conditions>()
    val temperatureColor = MutableLiveData<Int>()
    val isLoading = MutableLiveData<Boolean>().apply { value = false }

    private val event = LiveEvent<DetailsEvent>()
    val liveEvent: LiveData<DetailsEvent> = event

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun getConditions() {
        weatherUseCases.getConditions(city.value?.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true }
            .doFinally { isLoading.value = false }
            .subscribe(
                {
                    conditions.value = it
                    resolveTemperatureColor(it.temperature.metric.value)
                },
                {
                    event.value = DetailsEvent.Error(it.localizedMessage)
                }
            ).addTo(disposables)
    }

    private fun resolveTemperatureColor(temperatureValue: Float) {
        temperatureColor.value = when {
            temperatureValue < 10 -> R.color.blue
            temperatureValue < 20 -> R.color.black
            else -> R.color.red
        }
    }

    sealed class DetailsEvent {
        class Error(val error: String) : DetailsEvent()
    }

}