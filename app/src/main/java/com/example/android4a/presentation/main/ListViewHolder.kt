package com.example.android4a.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android4a.R
import com.example.android4a.data.repository.Pokemon

class ListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.row_layout, parent, false)) {
    private var mNameView: TextView? = null
    private var mSpeciesView: TextView? = null

    init {
        mNameView= itemView.findViewById(R.id.firstLine)
        mSpeciesView = itemView.findViewById(R.id.secondLine)
    }

    fun bind(pokemon: Pokemon) {
        mNameView?.text = pokemon.name
        mSpeciesView?.text = pokemon.species
    }

}