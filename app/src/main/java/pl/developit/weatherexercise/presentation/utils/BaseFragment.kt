package pl.developit.weatherexercise.presentation.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    protected fun showError(error: String) = Toast.makeText(context, error, Toast.LENGTH_SHORT).show()

}