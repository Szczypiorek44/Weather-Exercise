package pl.developit.weatherexercise.presentation.utils

import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.core.content.ContextCompat
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

@BindingAdapter("cityHistory")
fun AutoCompleteTextView.setCityHistory(cityHistory: Set<String>) {
    setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, cityHistory.toList()))
}

@BindingAdapter("showDropDownOnFocus")
fun AutoCompleteTextView.showDropDownOnFocus(showDropDownOnFocus: Boolean) {
    if (showDropDownOnFocus)
        onFocusChangeListener = View.OnFocusChangeListener { _, focused -> if (focused) showDropDown() }
}

@BindingAdapter("textColorResource")
fun TextView.setTextColorResource(textColorResource: Int) {
    if (textColorResource != 0) setTextColor(ContextCompat.getColor(context, textColorResource))
}