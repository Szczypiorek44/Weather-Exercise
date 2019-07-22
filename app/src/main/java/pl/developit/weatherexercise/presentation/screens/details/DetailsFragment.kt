package pl.developit.weatherexercise.presentation.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.developit.weatherexercise.R
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.databinding.FragmentDetailsBinding
import pl.developit.weatherexercise.presentation.screens.main.MainViewModel
import pl.developit.weatherexercise.presentation.utils.BaseFragment
import pl.developit.weatherexercise.presentation.utils.BundleDelegate

class DetailsFragment : BaseFragment() {

    private var Bundle.city by BundleDelegate.City("city")
    private var Bundle.conditions by BundleDelegate.Conditions("conditions")

    private val viewModel by viewModel<DetailsViewModel>()
    private val mainViewModel by sharedViewModel<MainViewModel>()

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
                    it.viewModel = viewModel
                }
        observeEvents()

        mainViewModel.showActionBarBackButton()

        viewModel.city.value = arguments?.city
        viewModel.getConditions()
        return binding.root
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

    private fun observeEvents() {
        viewModel.liveEvent.observe(this, Observer {
            when (it) {
                is DetailsViewModel.DetailsEvent.Error -> showError(it.error)
            }
        })
    }
}
