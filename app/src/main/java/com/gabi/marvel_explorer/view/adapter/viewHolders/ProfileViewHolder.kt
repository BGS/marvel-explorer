package com.gabi.marvel_explorer.view.adapter.viewHolders

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.gabi.marvel_explorer.BR
import com.gabi.marvel_explorer.model.FavoriteComics

import com.gabi.marvel_explorer.view.models.ProfileViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_comic_list_item.view.profileComicCover

class ProfileViewHolder constructor(private val dataBinding: ViewDataBinding, private val profileViewModel: ProfileViewModel)
    : RecyclerView.ViewHolder(dataBinding.root)  {

    val favoriteComic = itemView.profileComicCover
    fun setup(itemData: FavoriteComics) {
        dataBinding.setVariable(BR.itemData, itemData)
        dataBinding.executePendingBindings()
        Picasso.get().load(itemData.comicCoverUrl).into(favoriteComic)






            //itemView.findNavController().navigate(R.id.action_comicListFragment_to_comicDetailFragment,
            //        bundle)


    }

}