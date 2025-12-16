package com.example.recycleviewapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleviewapp.domain.RecyclerExampleData
import com.example.recycleviewapp.databinding.ViewHolderExampleBinding
import com.example.recycleviewapp.databinding.ViewHolderExampleSecondBinding

class RecyclerExampleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: List<RecyclerExampleData> = emptyList()

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is RecyclerExampleData.Title -> TITLE_TYPE
        is RecyclerExampleData.Item -> ITEM_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TITLE_TYPE -> ViewHolderTitle(
                ViewHolderExampleSecondBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ITEM_TYPE -> ViewHolderItem(
                ViewHolderExampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalStateException("Unsupported type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = list[position]) {
            is RecyclerExampleData.Title -> (holder as ViewHolderTitle).update(item)
            is RecyclerExampleData.Item -> (holder as ViewHolderItem).update(item)
        }
    }

    override fun getItemCount(): Int = list.size

    companion object {
        private const val ITEM_TYPE = 0
        private const val TITLE_TYPE = 1
    }
}
