package pl.developit.weatherexercise.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import pl.developit.weatherexercise.data.models.City

class MainViewModel : ViewModel() {

    private val event = LiveEvent<MainEvent>()
    val liveEvent: LiveData<MainEvent> = event

    fun showSearchFragment() {
        event.value = MainEvent.ShowSearchFragment
    }

    fun showDetailsFragment(city: City) {
        event.value = MainEvent.ShowDetailsFragment(city)
    }

    fun showActionBarBackButton() {
        event.value = MainEvent.ShowActionBarBackButton
    }

    fun hideActionBarBackButton() {
        event.value = MainEvent.HideActionBarBackButton
    }

    sealed class MainEvent {
        object ShowSearchFragment : MainEvent()
        class ShowDetailsFragment(val city: City) : MainEvent()
        object ShowActionBarBackButton : MainEvent()
        object HideActionBarBackButton : MainEvent()
    }
}