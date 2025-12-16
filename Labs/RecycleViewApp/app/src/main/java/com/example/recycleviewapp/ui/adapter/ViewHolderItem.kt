package com.example.recycleviewapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycleviewapp.databinding.ViewHolderExampleBinding
import com.example.recycleviewapp.domain.RecyclerExampleData

class ViewHolderItem(private val binding: ViewHolderExampleBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun update(item: RecyclerExampleData.Item) {
        binding.title.text = item.character.name
        binding.subtitle.text = "${item.character.species} - ${item.character.status}"
        Glide.with(binding.imageView.context)
            .load(item.character.image)
            .into(binding.imageView)
    }
}
