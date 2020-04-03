package com.gabi.marvel_explorer.model

import android.content.ContentValues.TAG
import android.util.Log
import com.gabi.marvel_explorer.model.api.ApiClient
import com.gabi.marvel_explorer.utils.FirebaseFirestoreUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.EventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetProfileData {

    fun getProfileData(onResult: (isSuccess: Boolean, response: List<FavoriteComics>?) -> Unit) {
        FirebaseFirestoreUtil.getCurrentUserProfileRef().addOnCompleteListener {
            if (it.isSuccessful) {
                val userData = it.result?.toObject(FirebaseUser::class.java)
                val favoriteComicData = userData?.favoriteComics
                onResult(true, favoriteComicData)
            } else
                onResult(false, null)
        }

    }


    companion object {
        private var INSTANCE: GetProfileData? = null
        fun getInstance() = INSTANCE
                ?: GetProfileData().also {
                    INSTANCE = it
                }
    }
}