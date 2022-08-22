package com.example.made_1.Detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.core.data.source.Resource
import com.example.core.domain.model.Creator
import com.example.made_1.R
import com.example.made_1.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private var isFavorite = false
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailCreator: Creator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val detailCreator = intent.getParcelableExtra<Creator>(EXTRA_DATA)

        if (detailCreator != null) {
            Log.w("getId",""+detailCreator.id)
            detailViewModel.getDetailCreator(detailCreator.id).observe(this) { detailCreator ->
                when (detailCreator) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        Log.w("getData", "" + detailCreator.data)
                        val dataCreator = detailCreator.data as Creator
                        detailViewModel.getDetailState(dataCreator.id)?.observe(this, { user ->
                            isFavorite = user.isFavorite == true
                            setStatusFavorite(isFavorite)
                            binding.fab.setOnClickListener {
                                Log.w("isFavor", "" + isFavorite)
                                detailViewModel.setFavoriteCreator(user)
                                setStatusFavorite(isFavorite)
                            }
                        })
                        populateData(dataCreator)
                        showLoading(false)

                    }
                    is Resource.Error -> {
                        Toast.makeText(this, "error : ${detailCreator.message}", Toast.LENGTH_SHORT)
                            .show()
                        showLoading(false)
                    }
                }
            }
        }

    }

    private fun populateData(detailCreator: Creator) {
        detailCreator?.let {
            supportActionBar?.title = detailCreator.name

            Glide.with(this@DetailActivity)
                .load(detailCreator.image_background)
                .into(binding.ivDetailImage)

            Glide.with(this@DetailActivity)
                .load(detailCreator.image)
                .circleCrop()
                .into(binding.content.imageView2)

            binding.content.name.text = detailCreator.name
            binding.content.description.text = detailCreator.description
            binding.content.gamesCount.text = detailCreator.games_count.toString()
            binding.content.reviewsCount.text = detailCreator.reviews_count.toString()
            binding.content.ratingBar.rating = if(detailCreator.rating.length > 0 ) detailCreator.rating.toFloat() else 0F
            binding.content.ratingBar.numStars = 5

        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_filled))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.isVisible = state
        binding.appBar.isVisible = !state
    }
}