package com.ahr.movie.ui.home

import android.os.Bundle
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
import com.ahr.movie.core_resource.adapter.MovieAdapter
import com.ahr.movie.core_resource.listener.OnMovieClickListener
import com.ahr.movie.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), OnMovieClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    private var movieAdapter: MovieAdapter? = null

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

        if (homeViewModel.firstLoad.value) {
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
            if (menu.itemId == R.id.favoriteFragment) {
                val toFavoriteFragment = HomeFragmentDirections
                    .actionHomeFragmentToFavoriteFragment3()
                findNavController().navigate(toFavoriteFragment)
            }
            true
        }

    }

    private fun observeMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.movies.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            toggleShimmer(true)
                            toggleLottieNoConnection(false)
                        }
                        is Resource.Success -> {
                            toggleShimmer(false)
                            toggleLottieNoConnection(false)
                            movieAdapter?.setMovies(resource.data)
                        }
                        is Resource.Error -> {
                            toggleShimmer(false)
                            toggleLottieNoConnection(true)
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

    private fun toggleLottieNoConnection(state: Boolean) {
        binding.lottieNoConnection.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieAdapter = null
        _binding = null
    }
}