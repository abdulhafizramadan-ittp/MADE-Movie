package com.ahr.movie.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.ahr.movie.R
import com.ahr.movie.core_domain.Resource
import com.ahr.movie.core_domain.models.Genre
import com.ahr.movie.core_domain.models.Movie
import com.ahr.movie.core_domain.models.MovieDetail
import com.ahr.movie.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    private var movie: Movie? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            detailViewModel.getMovieDetail(args.movieId)
        }

        detailViewModel.isFavoriteMovie(args.movieId)

        observeMovieDetail()

        binding.fabFavorite.setOnClickListener {
            movie?.let {
                detailViewModel.toggleFavorite(it)
            }
        }
    }

    private fun observeMovieDetail() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.movieDetail
                    .collect { resource ->
                        when (resource) {
                            is Resource.Loading -> toggleLoading(true)
                            is Resource.Success -> {
                                val movieDetail = resource.data
                                movieDetail?.let {
                                    movie = Movie(
                                        title = it.title,
                                        posterPath = it.posterPath,
                                        id = it.id
                                    )
                                    setupMovieDetail(it)
                                }
                                toggleLoading(false)
                            }
                            is Resource.Error -> toggleLoading(false)
                            is Resource.Empty -> {}
                        }
                    }

            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.isFavorite
                    .collect { isFavorite ->
                        val fabIcon = if (isFavorite) {
                            com.ahr.movie.core_resource.R.drawable.ic_favorite_active
                        } else {
                            com.ahr.movie.core_resource.R.drawable.ic_favorite
                        }

                        binding.fabFavorite.setImageResource(fabIcon)
                    }
            }
        }
    }

    private fun setupMovieDetail(movieDetail: MovieDetail) {
        binding.apply {
            Glide.with(this@DetailFragment)
                .load("http://image.tmdb.org/t/p/w500/${movieDetail.posterPath}")
                .placeholder(com.ahr.movie.core_resource.R.drawable.bg_loading_image)
                .error(com.ahr.movie.core_resource.R.drawable.bg_broken_image)
                .into(ivMovieDetail)

            tvMovieTitle.text = movieDetail.title
            tvMovieLanguage.text = getString(R.string.movie_language, movieDetail.originalLanguage)
            tvMoviePopularity.text = getString(R.string.movie_popularity, movieDetail.popularity)
            tvMovieOverview.text = movieDetail.overview

            setupChipGenres(movieDetail.genres)
        }
    }

    private fun setupChipGenres(genres: List<Genre>) {
        binding.chipGenres.removeAllViews()
        genres.forEach { (name, _) ->
            val chip = Chip(requireContext())
            chip.text = name
            chip.isClickable = false
            binding.chipGenres.addView(chip)
        }
    }

    private fun toggleLoading(state: Boolean) {
        binding.detailContainer.visibility = if (state) View.GONE else View.VISIBLE
        binding.detailShimmer.root.visibility = if (!state) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}