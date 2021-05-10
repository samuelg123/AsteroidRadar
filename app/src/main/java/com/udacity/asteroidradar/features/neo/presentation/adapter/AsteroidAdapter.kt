package com.udacity.asteroidradar.features.neo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.features.neo.domain.model.Asteroid

class AsteroidClickListener(val block: (Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = block(asteroid)
}

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class AsteroidAdapter(private val listener: AsteroidClickListener) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(AsteroidDiffCallback) {

    private companion object AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }
    }

    class AsteroidViewHolder(private val binding: AsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: AsteroidClickListener, asteroid: Asteroid) {
            binding.run {
                this.asteroid = asteroid
                clickListener = listener
                executePendingBindings()
            }
        }

        companion object {
            internal fun from(parent: ViewGroup): AsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)
                return AsteroidViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AsteroidViewHolder.from(parent)

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) =
        holder.bind(listener, getItem(position))

}
