package com.example.top10downloader

class FeedEntry {

    var name: String = ""
    var artist: String = ""
    var releaseDate: String = ""
    var summery: String = ""
    var imageUrl: String = ""

    override fun toString(): String {
        return """
            name = $name
            artist = $artist
            releaseDate = $releaseDate
            summery = $summery
            imageUrl = $imageUrl
        """.trimIndent()
    }
}