package com.gabi.marvel_explorer.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabi.marvel_explorer.databinding.ViewComicListItemBinding
import com.gabi.marvel_explorer.model.Item
import com.gabi.marvel_explorer.view.adapter.viewHolders.ComicListViewHolder
import com.gabi.marvel_explorer.view.models.ComicListViewModel

class ComicListAdapter(private val comicListViewModel: ComicListViewModel) : RecyclerView.Adapter<ComicListViewHolder>() {
    var comicList: List<Item> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewComicListItemBinding.inflate(inflater, parent, false)
        return ComicListViewHolder(dataBinding, comicListViewModel)
    }

    override fun getItemCount() = comicList.size

    override fun onBindViewHolder(holder: ComicListViewHolder, position: Int) {
        holder.setup(comicList[position])
    }

    fun updateComicList(comicList: List<Item>) {
        this.comicList = comicList
        notifyDataSetChanged()
    }
}