package com.gabi.marvel_explorer.model

data class MarvelApiResponseComics(
    val code: Int,
    val status: String,
    val data: Data
)

data class Data (
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<Item>
)
data class Item(
        val id: Int,
        val digitalId: Int,
        val title: String,
        val issueNumber: Int,
        val variantDescription: String,
        val description: String,
        val modified: String,
        val isbn: String,
        val upc: String,
        val diamondCode: Boolean,
        val ean: String,
        val issn: String,
        val format: String,
        val pageCount: String,
        val textObjects: List<Any>,
        val resourceURI: String,
        val urls: List<Preview>,
        val thumbnail: Thumbnail


)

data class Preview(
        val type: String,
        val url: String
)

data class Thumbnail(
        val path : String,
        val extension : String
)
