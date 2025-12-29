package com.example.tictactoeapp.leaderboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoeapp.R
import com.example.tictactoeapp.auth.User

class LeaderboardAdapter :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    private var items: List<User> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newList: List<User>) {
        items = newList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val position: TextView = view.findViewById(R.id.position)
        val username: TextView = view.findViewById(R.id.username)
        val wins: TextView = view.findViewById(R.id.wins)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = items[position]

        holder.position.text = "${position + 1}"
        holder.username.text = user.username
        holder.wins.text = "Победы: ${user.wins}"
    }
}
