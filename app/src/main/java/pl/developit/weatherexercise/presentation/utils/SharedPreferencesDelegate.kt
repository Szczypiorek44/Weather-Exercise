package pl.developit.weatherexercise.presentation.utils

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private inline fun <T> SharedPreferences.delegate(
    defaultValue: T,
    key: String?,
    crossinline getter: SharedPreferences.(String, T) -> T,
    crossinline setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>) =
            getter(key ?: property.name, defaultValue)

        override fun setValue(
            thisRef: Any, property: KProperty<*>,
            value: T
        ) =
            edit().setter(key ?: property.name, value).apply()
    }
}

fun SharedPreferences.stringSet(def: HashSet<String> = hashSetOf(), key: String? = null) =
    delegate(def, key, SharedPreferences::getStringSet, SharedPreferences.Editor::putStringSet)
