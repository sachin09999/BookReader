package com.project.bookreader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.project.bookreader.data.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor() : ViewModel() {
    private var currentBooks = mutableMapOf<String, Book>()

    fun setBook(book: Book) {
        currentBooks[book.id] = book
    }

    fun getBookById(id: String?): Book? {
        return id?.let { currentBooks[it] }
    }
}
