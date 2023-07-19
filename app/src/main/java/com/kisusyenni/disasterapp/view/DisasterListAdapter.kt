package com.kisusyenni.disasterapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kisusyenni.disasterapp.databinding.DisasterListItemBinding

class DisasterListAdapter: ListAdapter<String, DisasterListAdapter.ItemViewHolder>(ItemDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DisasterListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class ItemViewHolder(private val binding: DisasterListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindTo(text: String) {
            binding.apply {
                itemDisasterTitle.text = text
            }
        }
    }
}

class ItemDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

}