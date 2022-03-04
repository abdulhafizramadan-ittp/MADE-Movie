package com.ahr.movie.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ahr.movie.R
import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.databinding.FragmentHomeBinding
import com.ahr.movie.core_resource.adapter.MovieAdapter
import com.ahr.movie.core_resource.listener.OnMovieClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), OnMovieClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter(this)

        if (savedInstanceState == null) {
            homeViewModel.getAllMovies()
        }

        observeMovies()

        setupMenu()
        setupRvMovies()
    }

    override fun onMovieClickListener(movie: Movie) {
        val toDetailFragment = HomeFragmentDirections
            .actionHomeFragmentToDetailFragment(movie.id)
        findNavController()
            .navigate(toDetailFragment)
    }

    private fun setupMenu() {
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

    private fun observeMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.movies.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            toggleShimmer(true)
                        }
                        is Resource.Success -> {
                            Log.d("TAG", "observeMovies: Movie=${resource.data}")
                            toggleShimmer(false)
                            movieAdapter.setMovies(resource.data)
                        }
                        is Resource.Error -> {
                            toggleShimmer(false)
                        }
                        is Resource.Empty -> {}
                    }
                }
            }
        }
    }

    private fun setupRvMovies() {
        binding.rvMovie.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun toggleShimmer(state: Boolean) {
        binding.rvMovie.visibility = if (state) View.GONE else View.VISIBLE
        binding.homeShimmer.root.visibility = if (!state) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}