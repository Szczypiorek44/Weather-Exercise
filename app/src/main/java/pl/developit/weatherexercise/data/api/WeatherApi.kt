package pl.developit.weatherexercise.data.api

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.developit.weatherexercise.BuildConfig
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.data.models.Conditions
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    object Builder {

        private const val API_URL = "http://dataservice.accuweather.com/"

        fun build(): WeatherApi {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val objectMapper = ObjectMapper()
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

            val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()

            return retrofit.create(WeatherApi::class.java)
        }
    }

    @POST("locations/v1/cities/autocomplete")
    fun findCities(
        @Query("apikey") apiKey: String,
        @Query("q") city: String
    ): Single<List<City>>

    @GET("currentconditions/v1/{cityKey}")
    fun getCurrentConditions(
        @Path("cityKey") cityKey: Long,
        @Query("apikey") apiKey: String
    ): Single<List<Conditions>>
}