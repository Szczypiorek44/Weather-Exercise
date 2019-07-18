package pl.developit.weatherexercise.presentation.utils

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyChanged() {
	value = value
}