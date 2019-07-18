package pl.developit.weatherexercise.data.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Temperature(
    @JsonProperty("Metric") val metric: Model,
    @JsonProperty("Imperial") val imperial: Model
) : Parcelable {

    @Parcelize
    data class Model(
        @JsonProperty("Value") val value: Float,
        @JsonProperty("Unit") val unit: String
    ) : Parcelable
}