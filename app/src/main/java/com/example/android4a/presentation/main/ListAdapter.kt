package com.example.android4a.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android4a.data.repository.Pokemon

class ListAdapter(private val list: List<Pokemon>)
    : RecyclerView.Adapter<ListViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ListViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            val pokemon: Pokemon = list[position]
            holder.bind(pokemon)
        }

        override fun getItemCount(): Int = list.size

    }

