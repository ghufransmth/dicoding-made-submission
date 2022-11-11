package com.example.made_1.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.core.databinding.ItemCreatorsBinding
import com.example.core.domain.model.Creator
import com.example.made_1.R

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    private var listData = ArrayList<Creator>()
    var onItemClick: ((Creator) -> Unit)? = null

    fun setData(newListData: List<Creator>?) {
        if (newListData == null) return
        val diffResult = DiffUtil.calculateDiff(CreatorDiffUtilCallback(listData, newListData))
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_creators, parent, false))

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCreatorsBinding.bind(itemView)
        fun bind(data: Creator){
            with(binding) {
                val options = RequestOptions()
                    .override(binding.ivItemImage.width, binding.ivItemImage.height)

                Glide.with(itemView.context)
                    .load(data.image)
                    .placeholder(R.drawable.ic_launcher_background)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(options)
                    .into(ivItemImage)

                tvItemTitle.text = data.name
                tvItemSubtitle.text = data.slug

            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    class CreatorDiffUtilCallback(private val oldList: List<Creator>, private val newList: List<Creator>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return when {
                oldList[oldItemPosition].id == newList[newItemPosition].id -> true
                oldList[oldItemPosition].name == newList[newItemPosition].name -> true
                else -> false
            }
        }

    }
}