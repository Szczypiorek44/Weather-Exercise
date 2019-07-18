package pl.developit.weatherexercise.presentation

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.developit.weatherexercise.di.viewModelFactoryModule
import pl.developit.weatherexercise.di.weatherModule

class App : MultiDexApplication() {

	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@App)
			modules(listOf(viewModelFactoryModule, weatherModule))
		}
	}
}