package com.gabi.marvel_explorer.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabi.marvel_explorer.databinding.ViewProfileFavoriteComicListItemBinding
import com.gabi.marvel_explorer.model.FavoriteComics
import com.gabi.marvel_explorer.model.FirebaseUser
import com.gabi.marvel_explorer.view.adapter.viewHolders.ProfileViewHolder
import com.gabi.marvel_explorer.view.models.ProfileViewModel

class UserProfileAdapter (private val profileViewModel: ProfileViewModel) : RecyclerView.Adapter<ProfileViewHolder>() {
     var userData: List<FavoriteComics> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewProfileFavoriteComicListItemBinding.inflate(inflater, parent, false)
        return ProfileViewHolder(dataBinding, profileViewModel)
    }


    override fun getItemCount() = userData.size

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.setup(userData[position])
    }


    fun updateFavoriteComicsData( favoriteComics: List<FavoriteComics>) {

        this.userData = favoriteComics
        notifyDataSetChanged()
    }

}