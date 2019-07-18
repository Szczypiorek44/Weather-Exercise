package pl.developit.weatherexercise.presentation.utils

import android.os.Bundle
import pl.developit.weatherexercise.data.models.City
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

sealed class BundleDelegate<T>(protected val key: kotlin.String) : ReadWriteProperty<Bundle, T> {

    class String(key: kotlin.String) : BundleDelegate<kotlin.String>(key) {
        override fun setValue(thisRef: Bundle, property: KProperty<*>, value: kotlin.String) {
            thisRef.putString(key, value)
        }

        override fun getValue(thisRef: Bundle, property: KProperty<*>): kotlin.String {
            return thisRef.getString(key) ?: ""
        }
    }

    class CityList(key: kotlin.String) : BundleDelegate<List<pl.developit.weatherexercise.data.models.City>>(key) {

        override fun setValue(thisRef: Bundle, property: KProperty<*>, value: List<pl.developit.weatherexercise.data.models.City>) {
            thisRef.putParcelableArrayList(key, ArrayList(value))
        }

        override fun getValue(thisRef: Bundle, property: KProperty<*>): List<pl.developit.weatherexercise.data.models.City> {
            return thisRef.getParcelableArrayList(key) ?: ArrayList()
        }
    }

    class City(key: kotlin.String) : BundleDelegate<pl.developit.weatherexercise.data.models.City>(key) {
        override fun setValue(thisRef: Bundle, property: KProperty<*>, value: pl.developit.weatherexercise.data.models.City) {
            thisRef.putParcelable(key, value)
        }

        override fun getValue(thisRef: Bundle, property: KProperty<*>): pl.developit.weatherexercise.data.models.City {
            return thisRef.getParcelable(key) ?: City(0, "")
        }
    }

    class Conditions(key: kotlin.String) : BundleDelegate<pl.developit.weatherexercise.data.models.Conditions>(key) {
        override fun setValue(thisRef: Bundle, property: KProperty<*>, value: pl.developit.weatherexercise.data.models.Conditions) {
            thisRef.putParcelable(key, value)
        }

        override fun getValue(thisRef: Bundle, property: KProperty<*>): pl.developit.weatherexercise.data.models.Conditions {
            return thisRef.getParcelable(key)
        }
    }
}