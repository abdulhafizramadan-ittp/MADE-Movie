package com.ahr.movie.feature_favorite

import android.content.Context
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
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_resource.adapter.MovieAdapter
import com.ahr.movie.core_resource.listener.OnMovieClickListener
import com.ahr.movie.di.FavoriteModuleDependencies
import com.ahr.movie.feature_favorite.databinding.FragmentFavoriteBinding
import com.ahr.movie.feature_favorite.di.DaggerFavoriteComponent
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment : Fragment(), OnMovieClickListener {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        viewModelFactory
    }

    lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    FavoriteModuleDependencies::class.java
                ))
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter(this)

        if (favoriteViewModel.firstLoad.value) {
            favoriteViewModel.getFavoriteMovies()
        }

        observeFavoriteMovies()
        setupRvMovies()
    }

    override fun onMovieClickListener(movie: Movie) {
        val toDetailFragment = FavoriteFragmentDirections
            .actionFavoriteFragmentToDetailFragment(movie.id)
        findNavController()
            .navigate(toDetailFragment)
    }

    private fun observeFavoriteMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteViewModel.favoriteMovies.collect { favoriteMovies ->
                    movieAdapter.setMovies(favoriteMovies)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}