package com.project.bookreader.data.repository

import com.project.bookreader.data.model.Book
import com.project.bookreader.data.remote.BookApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val api: BookApi
) {
    fun searchBooks(query: String) = flow {
        try {
            val response = api.searchBooks(query)
            val books = response.items.map { bookItem ->
                Book(
                    id = bookItem.id,
                    title = bookItem.volumeInfo.title,
                    authors = bookItem.volumeInfo.authors ?: listOf("Unknown Author"),
                    description = bookItem.volumeInfo.description,
                    thumbnail = bookItem.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:"),
                    previewLink = bookItem.volumeInfo.previewLink ?: "",
                    categories = bookItem.volumeInfo.categories,
                    pageCount = bookItem.volumeInfo.pageCount,
                    averageRating = bookItem.volumeInfo.averageRating,
                    publishedDate = bookItem.volumeInfo.publishedDate
                )
            }
            emit(Result.success(books))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getBooksByCategory(category: String) = flow {
        try {
            val response = api.getBooksByCategory("subject:$category", category)
            val books = response.items.map { bookItem ->
                Book(
                    id = bookItem.id,
                    title = bookItem.volumeInfo.title,
                    authors = bookItem.volumeInfo.authors ?: listOf("Unknown Author"),
                    description = bookItem.volumeInfo.description,
                    thumbnail = bookItem.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:"),
                    previewLink = bookItem.volumeInfo.previewLink ?: "",
                    categories = bookItem.volumeInfo.categories,
                    pageCount = bookItem.volumeInfo.pageCount,
                    averageRating = bookItem.volumeInfo.averageRating,
                    publishedDate = bookItem.volumeInfo.publishedDate
                )
            }
            emit(Result.success(books))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
