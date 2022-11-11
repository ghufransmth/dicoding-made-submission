package com.example.made_1.Detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.data.source.Resource
import com.example.core.domain.model.Creator
import com.example.made_1.R
import com.example.made_1.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
    private var isFavorite = false
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val detailCreator = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, Creator::class.java)
        } else {
            intent.getParcelableExtra<Creator>(EXTRA_DATA)
        }

        if (detailCreator != null) {
            Log.w("getId",""+detailCreator.id)
            detailViewModel.getDetailCreator(detailCreator.id).observe(this) { detCreator ->
                when (detCreator) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        Log.w("getData", "" + detCreator.data)
                        val dataCreator = detCreator.data as Creator
                        detailViewModel.getDetailState(dataCreator.id)?.observe(this) { user ->
                            isFavorite = user.isFavorite == true
                            setStatusFavorite(isFavorite)
                            binding.fab.setOnClickListener {
                                Log.w("isFavor", "" + isFavorite)
                                detailViewModel.setFavoriteCreator(user)
                                setStatusFavorite(isFavorite)
                            }
                        }
                        populateData(dataCreator)
                        showLoading(false)

                    }
                    is Resource.Error -> {
                        Toast.makeText(this, "error : ${detCreator.message}", Toast.LENGTH_SHORT)
                            .show()
                        showLoading(false)
                    }
                }
            }
        }

    }

    private fun populateData(detailCreator: Creator) {
        detailCreator.let {
            supportActionBar?.title = detailCreator.name

            binding?.apply {
                ivDetailImage.loadImage(detailCreator.image_background)
                content.imageView2.loadImage(detailCreator.image)

                content.name.text = detailCreator.name
                content.description.text = detailCreator.description
                content.gamesCount.text = detailCreator.games_count.toString()
                content.reviewsCount.text = detailCreator.reviews_count.toString()
                content.ratingBar.rating = if(detailCreator.rating.isNotEmpty()) detailCreator.rating.toFloat() else 0F
                content.ratingBar.numStars = 5
            }

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

    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context) .load(url) .apply(
        RequestOptions().override(500, 500)) .centerCrop() .into(this)
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}