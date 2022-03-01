package com.ahr.movie.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ahr.movie.R
import com.ahr.movie.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setOnMenuItemClickListener {  menu ->
            when (menu.itemId) {
                R.id.searchFragment -> {
                    val toSearchFragment = HomeFragmentDirections
                        .actionHomeFragmentToSearchFragment()
                    findNavController()
                        .navigate(toSearchFragment)
                    true
                }
                R.id.favoriteFragment -> {
                    val toFavoriteFragment = HomeFragmentDirections
                        .actionHomeFragmentToFavoriteFragment()
                    findNavController().navigate(toFavoriteFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}