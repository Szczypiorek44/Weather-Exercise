package pl.developit.weatherexercise.presentation.screens.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pl.developit.weatherexercise.R
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.presentation.screens.search.viewholders.CityViewHolder


class CityAdapter(private val onCityClickCallback: OnCityClickCallback) :
    ListAdapter<City, CityViewHolder>(CityDiff()) {

    class CityDiff : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CityViewHolder {
        return CityViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_city,
                viewGroup,
                false
            ), onCityClickCallback
        )
    }

    override fun onBindViewHolder(viewHolder: CityViewHolder, i: Int) {
        viewHolder.bind(getItem(i))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).key
    }

    interface OnCityClickCallback {
        fun onCityClick(city: City)
    }
}