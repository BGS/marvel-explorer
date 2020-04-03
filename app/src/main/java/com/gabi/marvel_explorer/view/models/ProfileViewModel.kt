package com.gabi.marvel_explorer.view.models

import androidx.lifecycle.MutableLiveData
import com.gabi.marvel_explorer.model.*
import com.gabi.marvel_explorer.view.models.base.BaseViewModel

class ProfileViewModel : BaseViewModel() {
    val profileData = MutableLiveData<List<FavoriteComics>>()


    fun fetchProfileData() {
        dataLoading.value = true
        GetProfileData.getInstance().getProfileData { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                profileData.value = response

                empty.value = false
            } else {
                empty.value = true
            }
        }
    }
}