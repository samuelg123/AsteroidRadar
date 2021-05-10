package com.udacity.asteroidradar.features.neo.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.features.neo.presentation.adapter.AsteroidAdapter
import com.udacity.asteroidradar.features.neo.presentation.adapter.AsteroidClickListener
import com.udacity.asteroidradar.features.neo.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = AsteroidAdapter(AsteroidClickListener {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it.codename,it))
        })

        binding.asteroidRecycler.adapter = adapter

        return binding.root
    }

}
