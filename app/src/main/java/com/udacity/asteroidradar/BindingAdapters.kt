package com.udacity.asteroidradar

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.core.viewmodel.Status
import com.udacity.asteroidradar.features.neo.domain.model.Asteroid
import com.udacity.asteroidradar.features.neo.presentation.adapter.AsteroidAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("listAsteroids")
fun RecyclerView.bindAsteroids(data: List<Asteroid>?) {
    val adapter = adapter as AsteroidAdapter
    adapter.submitList(data) {
        scrollToPosition(0)
    }
}

@BindingAdapter("imageUrl", "error")
fun loadImage(view: ImageView, url: String?, error: Drawable) {
    Picasso.get().load(url).error(error).into(view)
}

@BindingAdapter("viewModelStatus")
fun ProgressBar.bindViewModelStatus(status: Status) {
    visibility = when (status) {
        Status.INIT, Status.LOADING -> {
            View.VISIBLE
        }
        Status.DONE, Status.ERROR -> {
            View.GONE
        }
    }
}
