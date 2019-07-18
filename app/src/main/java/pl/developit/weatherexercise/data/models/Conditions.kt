package pl.developit.weatherexercise.data.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Conditions(
    @JsonProperty("WeatherText") val weatherText: String,
    @JsonProperty("IsDayTime") val isDayTime: Boolean,
    @JsonProperty("Temperature") val temperature: Temperature
): Parcelable