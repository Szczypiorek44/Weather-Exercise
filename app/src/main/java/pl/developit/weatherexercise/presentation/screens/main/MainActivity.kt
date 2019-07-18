package pl.developit.weatherexercise.presentation.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import pl.developit.weatherexercise.R

class MainActivity : AppCompatActivity(), MainCommunicator {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) // if savedInstanceState is not null then it means that we are returning to the app and fragment has been saved = no need to show new fragment
            return
        else
            showSearchFragment()
    }

    override fun provideFragmentManager(): FragmentManager = supportFragmentManager

    override fun showActionBarBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}