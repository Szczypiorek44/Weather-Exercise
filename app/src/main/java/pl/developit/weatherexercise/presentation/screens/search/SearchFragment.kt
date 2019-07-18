package pl.developit.weatherexercise.presentation.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.viewModel
import pl.developit.weatherexercise.R
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.databinding.FragmentSearchBinding
import pl.developit.weatherexercise.presentation.utils.BaseFragment
import pl.developit.weatherexercise.presentation.utils.BundleDelegate

class SearchFragment : BaseFragment(), SearchListener {

    private var Bundle.cityName by BundleDelegate.String("city")
    private var Bundle.cityList by BundleDelegate.CityList("cityList")

    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding =
            DataBindingUtil.inflate<FragmentSearchBinding>(inflater, R.layout.fragment_search, container, false).also {
                it.lifecycleOwner = this
                it.listener = this
                it.viewModel = viewModel
            }

        viewModel.liveEvent.observe(this, Observer {
            when (it) {
                is SearchViewModel.SearchEvent.Error -> showError(it.error)
            }
        })
        viewModel.getCityHistory()
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.cityName.value?.let { outState.cityName = it }
        viewModel.cityList.value?.let { outState.cityList = it }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            viewModel.cityName.value = it.cityName
            viewModel.cityList.value = it.cityList
        }
    }

    override fun onRefresh() {
        viewModel.findCities()
    }

    override fun onCityClick(city: City) {
        mainCommunicator.showDetailsFragment(city)
    }

}