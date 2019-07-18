package pl.developit.weatherexercise.presentation.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import pl.developit.weatherexercise.presentation.screens.main.MainCommunicator

abstract class BaseFragment : Fragment() {

    val mainCommunicator by lazy {
        activity as MainCommunicator
    }

    protected fun showError(error: String) = Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

}