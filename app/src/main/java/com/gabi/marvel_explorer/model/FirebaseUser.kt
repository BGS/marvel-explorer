package com.gabi.marvel_explorer.model


import com.google.firebase.database.PropertyName


data class FirebaseUser (
        @PropertyName("userUID") val uid: String,
        @PropertyName("userName") val displayName: String,
        @PropertyName("userEmail") val email: String,
        @PropertyName("profilePicUrl") val accountPicUrl: String,
        @PropertyName("favoriteComics") var favoriteComics: List<FavoriteComics>
) {
    constructor() : this("", "", "", "", emptyList())
}



data class FavoriteComics (
        @PropertyName("comicCoverUrl") val comicCoverUrl : String?,
        @PropertyName("comicName") val comicTitle : String?
) { constructor() : this("", "")}