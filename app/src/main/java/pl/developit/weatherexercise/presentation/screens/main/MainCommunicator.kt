package pl.developit.weatherexercise.presentation.screens.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pl.developit.weatherexercise.R
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.presentation.screens.details.DetailsFragment
import pl.developit.weatherexercise.presentation.screens.search.SearchFragment

interface MainCommunicator {

    fun provideFragmentManager(): FragmentManager

    fun showActionBarBackButton()

    fun showSearchFragment() {
        showFragment(SearchFragment())
    }

    fun showDetailsFragment(city: City) {
        showFragment(DetailsFragment.newInstance(city), true)
    }

    private fun showFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val fragmentTransaction = provideFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        if (addToBackStack) fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commitAllowingStateLoss()
    }
}