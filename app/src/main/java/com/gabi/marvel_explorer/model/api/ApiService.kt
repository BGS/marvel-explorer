package com.gabi.marvel_explorer.model.api


import com.gabi.marvel_explorer.model.MarvelApiResponseComics
import com.gabi.marvel_explorer.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest

interface ApiService {

    @GET("comics")
    fun getComics(@Query("limit") lim: Int = 15,
                  @Query("apikey") key: String = Constants.MARVEL_API_KEY,
                  @Query("ts") stamp: String = (System.currentTimeMillis()/1000).toString(),
                  @Query("hash") hash: String = String.format("%032x",
                          BigInteger(1, MessageDigest.getInstance("md5").digest(
                          ((System.currentTimeMillis()/1000).toString()
                                  + Constants.MARVEL_API_PRIV_KEY
                                  + Constants.MARVEL_API_KEY).toByteArray()
                  )))

                  ): Call<MarvelApiResponseComics>
}


