package com.gabi.marvel_explorer.view.models

import androidx.lifecycle.MutableLiveData
import com.gabi.marvel_explorer.model.Item
import com.gabi.marvel_explorer.model.GetComicsList
import com.gabi.marvel_explorer.view.models.base.BaseViewModel

class ComicListViewModel : BaseViewModel() {
    val comicListLive = MutableLiveData<List<Item>>()

    fun fetchComicList() {
        dataLoading.value = true
        GetComicsList.getInstance().getRepoList { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                comicListLive.value = response?.data?.results
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }
}