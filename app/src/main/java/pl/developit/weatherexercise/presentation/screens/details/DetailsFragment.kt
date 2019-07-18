package pl.developit.weatherexercise.presentation.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import pl.developit.weatherexercise.R
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.data.models.Temperature
import pl.developit.weatherexercise.databinding.FragmentDetailsBinding
import pl.developit.weatherexercise.presentation.utils.BaseFragment
import pl.developit.weatherexercise.presentation.utils.BundleDelegate

class DetailsFragment : BaseFragment(), DetailsListener {

    private var Bundle.city by BundleDelegate.City("city")
    private var Bundle.conditions by BundleDelegate.Conditions("conditions")

    private val viewModel by viewModel<DetailsViewModel>()

    companion object {
        fun newInstance(city: City) = DetailsFragment().apply {
            arguments = Bundle().apply { this.city = city }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding =
            DataBindingUtil.inflate<FragmentDetailsBinding>(inflater, R.layout.fragment_details, container, false)
                .also {
                    it.lifecycleOwner = this
                    it.listener = this
                    it.viewModel = viewModel
                }
        mainCommunicator.showActionBarBackButton()

        viewModel.city.value = arguments?.city
        viewModel.liveEvent.observe(this, Observer {
            when (it) {
                is DetailsViewModel.DetailsEvent.Error -> showError(it.error)
                is DetailsViewModel.DetailsEvent.ConditionsReady -> resolveTemperatureColor(it.conditions.temperature.metric)
            }
        })
        viewModel.getConditions()
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.city.value?.let { outState.city = it }
        viewModel.conditions.value?.let { outState.conditions = it }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            viewModel.city.value = it.city
            viewModel.conditions.value = it.conditions
        }
    }

    private fun resolveTemperatureColor(temperatureModel: Temperature.Model) {
        context?.let {
            when {
                temperatureModel.value < 10 -> temperatureTextView.setTextColor(
                    ContextCompat.getColor(it, R.color.blue)
                )
                temperatureModel.value < 20 -> temperatureTextView.setTextColor(
                    ContextCompat.getColor(it, R.color.black)
                )
                else -> temperatureTextView.setTextColor(ContextCompat.getColor(it, R.color.red))
            }
        }

    }
}
