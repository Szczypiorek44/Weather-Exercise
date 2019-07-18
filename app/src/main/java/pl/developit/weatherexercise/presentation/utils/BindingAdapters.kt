package pl.developit.weatherexercise.presentation.utils

import android.R
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.presentation.screens.search.adapters.CityAdapter

@BindingAdapter("cityList", "onCityClick")
fun RecyclerView.setCities(cityList: List<City>?, onCityClickCallback: CityAdapter.OnCityClickCallback) {
    if (adapter == null)
        adapter = CityAdapter(onCityClickCallback)
    (adapter as CityAdapter).submitList(cityList)
}

@BindingAdapter("onRefreshListener")
fun SwipeRefreshLayout.setOnRefreshListener(onRefreshListener: SwipeRefreshLayout.OnRefreshListener) {
    setOnRefreshListener(onRefreshListener)
}

@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.isRefreshing(isRefreshing: Boolean) {
    setRefreshing(isRefreshing)
}

@BindingAdapter("cityHistory", "showDropDownOnFocus")
fun AutoCompleteTextView.setCityHistory(cityHistory: Set<String>, showDropDownOnFocus: Boolean) {
    setAdapter(ArrayAdapter(context, R.layout.simple_list_item_1, cityHistory.toList()))
    if (showDropDownOnFocus)
        onFocusChangeListener = View.OnFocusChangeListener { _, focused -> if (focused) showDropDown() }
}