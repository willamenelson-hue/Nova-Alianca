package com.example.data.model

enum class HymnCollection(val displayName: String, val publisher: String) {
    HARPA_CRISTA("Harpa Cristã", "CPAD"),
    CANTOR_CRISTAO("Cantor Cristão", "JUERP - Convenção Batista")
}

data class Hymn(
    val number: Int,
    val title: String,
    val collection: HymnCollection,
    val author: String = "Tradicional",
    val lyrics: String,
    val chorus: String? = null,
    val isFavorite: Boolean = false
)

data class GospelSong(
    val id: String,
    val title: String,
    val artist: String,
    val lyricsUrl: String,
    val lyricsSnippet: String,
    val fullLyrics: String
)
