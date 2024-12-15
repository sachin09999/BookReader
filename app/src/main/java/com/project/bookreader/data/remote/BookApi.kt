package com.project.bookreader.data.remote

import com.project.bookreader.data.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 40,
        @Query("startIndex") startIndex: Int = 0
    ): BookResponse

    @GET("volumes")
    suspend fun getBooksByCategory(
        @Query("q") query: String,
        @Query("subject") category: String,
        @Query("maxResults") maxResults: Int = 40
    ): BookResponse

    companion object {
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }
}
