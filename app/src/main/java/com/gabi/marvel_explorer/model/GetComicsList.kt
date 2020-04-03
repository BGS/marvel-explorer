package com.gabi.marvel_explorer.model

import com.gabi.marvel_explorer.model.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetComicsList {

    // GET comic list
    fun getComicList(onResult: (isSuccess: Boolean, response: MarvelApiResponseComics?) -> Unit) {

        ApiClient.instance.getComics().enqueue(object : Callback<MarvelApiResponseComics> {
            override fun onResponse(call: Call<MarvelApiResponseComics>?, response: Response<MarvelApiResponseComics>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body()!!)
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<MarvelApiResponseComics>?, t: Throwable?) {
                onResult(false, null)
            }

        })
    }

    companion object {
        private var INSTANCE: GetComicsList? = null
        fun getInstance() = INSTANCE
                ?: GetComicsList().also {
                    INSTANCE = it
                }
    }
}