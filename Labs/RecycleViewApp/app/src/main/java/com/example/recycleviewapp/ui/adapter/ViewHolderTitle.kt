package com.example.recycleviewapp.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.recycleviewapp.databinding.ViewHolderExampleSecondBinding
import com.example.recycleviewapp.domain.RecyclerExampleData

class ViewHolderTitle(private val binding: ViewHolderExampleSecondBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun update(item: RecyclerExampleData.Title) {
        binding.title.text = item.text
    }
}
