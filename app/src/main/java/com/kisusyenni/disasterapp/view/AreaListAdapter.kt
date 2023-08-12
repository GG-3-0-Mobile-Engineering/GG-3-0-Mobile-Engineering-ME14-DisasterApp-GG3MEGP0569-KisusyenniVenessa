package com.kisusyenni.disasterapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kisusyenni.disasterapp.databinding.AreaListItemBinding
import com.kisusyenni.disasterapp.utils.Area

class AreaListAdapter : ListAdapter<Area, AreaListAdapter.AreaViewHolder>(AreaItemDiffCallback()) {

    private lateinit var onAreaClickCallback: OnAreaClickCallback

    fun setOnAreaClickCallback(onItemClickCallback: OnAreaClickCallback) {
        this.onAreaClickCallback = onItemClickCallback
    }

    interface OnAreaClickCallback {
        fun onItemClicked(area: Area, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaViewHolder {
        val binding = AreaListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AreaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AreaViewHolder, position: Int) {
        val data = getItem(position)
        holder.bindTo(data, position)
    }

    inner class AreaViewHolder(private val binding: AreaListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindTo(area: Area, position: Int) {
            binding.tvAreaItem.text = area.province
            binding.tvAreaItem.setOnClickListener {
                onAreaClickCallback.onItemClicked(area, position)
            }
        }
    }

}

class AreaItemDiffCallback: DiffUtil.ItemCallback<Area>() {
    override fun areItemsTheSame(oldItem: Area, newItem: Area): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Area, newItem: Area): Boolean = oldItem == newItem

}