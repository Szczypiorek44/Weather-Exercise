package pl.developit.weatherexercise.data.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    @JsonProperty("Key") val key: Long,
    @JsonProperty("LocalizedName") val name: String
): Parcelable