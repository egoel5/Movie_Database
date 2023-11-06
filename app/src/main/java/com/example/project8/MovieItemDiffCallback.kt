package com.example.project8

import androidx.recyclerview.widget.DiffUtil
import com.example.project8.model.OmdbMovie

class MovieItemDiffCallback : DiffUtil.ItemCallback<OmdbMovie>() {
    override fun areItemsTheSame(oldItem: OmdbMovie, newItem: OmdbMovie)
            = (oldItem.name == newItem.name)
    override fun areContentsTheSame(oldItem: OmdbMovie, newItem: OmdbMovie) = (oldItem == newItem)
}