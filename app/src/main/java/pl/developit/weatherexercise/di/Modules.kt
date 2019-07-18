package pl.developit.weatherexercise.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.developit.weatherexercise.R
import pl.developit.weatherexercise.data.api.WeatherApi
import pl.developit.weatherexercise.domain.WeatherInteractor
import pl.developit.weatherexercise.domain.WeatherUseCases
import pl.developit.weatherexercise.presentation.screens.details.DetailsViewModel
import pl.developit.weatherexercise.presentation.screens.search.SearchViewModel

val viewModelFactoryModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}

val weatherModule = module {
    single<WeatherUseCases> { WeatherInteractor(androidContext(), get(), get()) }
    single { WeatherApi.Builder.build() }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            androidContext().getString(R.string.sharedpreferences_file_key),
            Context.MODE_PRIVATE
        )
    }
}