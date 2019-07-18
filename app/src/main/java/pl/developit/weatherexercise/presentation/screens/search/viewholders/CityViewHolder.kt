package pl.developit.weatherexercise.presentation.screens.search.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pl.developit.weatherexercise.data.models.City
import pl.developit.weatherexercise.databinding.ItemCityBinding
import pl.developit.weatherexercise.presentation.screens.search.adapters.CityAdapter

class CityViewHolder(
    private val binding: ItemCityBinding,
    private val callback: CityAdapter.OnCityClickCallback
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    override fun onClick(v: View?) {
        binding.invalidateAll()
        callback.onCityClick(binding.city!!)
    }

    fun bind(city: City) {
        binding.city = city
        binding.listener = this
    }

}