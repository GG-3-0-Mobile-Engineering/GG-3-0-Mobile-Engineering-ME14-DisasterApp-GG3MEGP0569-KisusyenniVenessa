package com.kisusyenni.disasterapp.view

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kisusyenni.disasterapp.R
import com.kisusyenni.disasterapp.data.api.ReportsGeometriesItem
import com.kisusyenni.disasterapp.data.api.ReportsProperties
import com.kisusyenni.disasterapp.databinding.DisasterListItemBinding
import com.kisusyenni.disasterapp.utils.formatDate

class DisasterListAdapter: ListAdapter<ReportsGeometriesItem, DisasterListAdapter.ItemViewHolder>(ItemDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DisasterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null) {
            data.properties?.let { holder.bindTo(it) }
        }
    }

    inner class ItemViewHolder(private val binding: DisasterListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindTo(data: ReportsProperties) {
            val itemContext = itemView.context
            binding.apply {
                itemDisasterSrc.let { tvSrc ->
                    tvSrc.text = data.source
                    tvSrc.visibility = if(data.source.isNullOrBlank()) View.GONE else View.VISIBLE
                }
                itemDisasterTitle.text = data.title ?: itemContext.getString(R.string.item_disaster_empty_title)
                itemDisasterDesc.let { tvSrc ->
                    if(data.text.isNullOrBlank()) {
                        tvSrc.text = itemContext.getString(R.string.item_disaster_empty_desc)
                        tvSrc.setTypeface(null, Typeface.ITALIC)
                    } else {
                        tvSrc.text = data.text
                    }
                }
                itemDisasterCreatedAt.text = itemContext.getString(R.string.item_disaster_created_at, data.createdAt?.let {
                    formatDate(it)
                }
                    ?: "-")
                Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(14)))
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.default_img)
                    .into(ivItemDisaster)
            }
        }
    }
}

class ItemDiffCallback: DiffUtil.ItemCallback<ReportsGeometriesItem>() {
    override fun areItemsTheSame(oldItem: ReportsGeometriesItem, newItem: ReportsGeometriesItem): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: ReportsGeometriesItem, newItem: ReportsGeometriesItem): Boolean = oldItem == newItem

}