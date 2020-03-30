package com.gabi.marvel_explorer.view.adapter.viewHolders

import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.gabi.marvel_explorer.BR
import com.gabi.marvel_explorer.R
import com.gabi.marvel_explorer.model.Item
import com.gabi.marvel_explorer.view.models.ComicListViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_comic_list_item.view.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.sdk27.coroutines.onClick

class ComicListViewHolder constructor(private val dataBinding: ViewDataBinding, private val comicListViewModel: ComicListViewModel)
    : RecyclerView.ViewHolder(dataBinding.root) {

    val avatarImage = itemView.comicCoverView
    val comicName = itemView.comicName

    fun setup(itemData: Item) {
        dataBinding.setVariable(BR.itemData, itemData)
        dataBinding.executePendingBindings()
        comicName.text = itemData.title
        val imageUrl = itemData.thumbnail.path + "/portrait_large." + itemData.thumbnail.extension
        val coverImageUrl = itemData.thumbnail.path + "/landscape_large." + itemData.thumbnail.extension
        val comicTitle = itemData.title.ifEmpty { "Marvel Explorer" }
        Picasso.get().setLoggingEnabled(true)
        Picasso.get().load(imageUrl).fit().into(avatarImage)

        itemView.onClick {
           val bundle = bundleOf("url" to itemData.urls[0].url,
                   "cover_url" to coverImageUrl,
                   "comic_title" to comicTitle
           )
            itemView.findNavController().navigate(R.id.action_comicListFragment_to_comicDetailFragment,
                    bundle)

        }
    }
}