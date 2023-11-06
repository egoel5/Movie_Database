package com.example.project8

class MovieList {
    val imdbID: String
    val Title: String
    val Year: String
    val Runtime: String
    val Poster: String
    val Rated: String
    val imdbRating: String
    val Genre: String

    constructor(imdbID: String, Title: String, Year: String, Runtime: String, Poster: String, Rated: String, imdbRating: String, Genre: String) {
        this.imdbID = imdbID
        this.Title = Title
        this.Year = Year
        this.Runtime = Runtime
        this.Poster = Poster
        this.Rated = Rated
        this.imdbRating = imdbRating
        this.Genre = Genre
    }
}