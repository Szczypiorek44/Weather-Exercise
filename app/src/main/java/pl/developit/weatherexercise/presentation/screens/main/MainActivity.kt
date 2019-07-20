package pl.developit.weatherexercise.presentation.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.developit.weatherexercise.R
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.presentation.screens.details.DetailsFragment
import pl.developit.weatherexercise.presentation.screens.main.MainViewModel.MainEvent
import pl.developit.weatherexercise.presentation.screens.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        observeEvents()

        if (savedInstanceState != null) return  // if savedInstanceState is not null then it means that we are returning to the app
        else viewModel.showSearchFragment()     // fragment has been saved = no need to show new fragment
    }

    private fun observeEvents() {
        viewModel.liveEvent.observe(this, Observer {
            when (it) {
                is MainEvent.ShowSearchFragment -> showSearchFragment()
                is MainEvent.ShowDetailsFragment -> showDetailsFragment(it.city)
                is MainEvent.ShowActionBarBackButton -> showActionBarBackButton()
                is MainEvent.HideActionBarBackButton -> hideActionBarBackButton()
            }
        })
    }

    private fun showSearchFragment() {
        showFragment(SearchFragment())
    }

    private fun showDetailsFragment(city: City) {
        showFragment(DetailsFragment.newInstance(city), true)
    }

    private fun showFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        if (addToBackStack) fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun showActionBarBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun hideActionBarBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}