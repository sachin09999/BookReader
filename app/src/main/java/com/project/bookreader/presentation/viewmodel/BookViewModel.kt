package com.project.bookreader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.bookreader.data.model.Book
import com.project.bookreader.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Book>>(emptyList())
    val searchResults: StateFlow<List<Book>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                repository.searchBooks(query)
                    .catch { e ->
                        _error.value = e.message
                    }
                    .collect { result ->
                        result.fold(
                            onSuccess = { books ->
                                _searchResults.value = books
                            },
                            onFailure = { e ->
                                _error.value = e.message
                            }
                        )
                    }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getBooksByCategory(category: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                repository.getBooksByCategory(category)
                    .catch { e ->
                        _error.value = e.message
                    }
                    .collect { result ->
                        result.fold(
                            onSuccess = { books ->
                                _searchResults.value = books
                            },
                            onFailure = { e ->
                                _error.value = e.message
                            }
                        )
                    }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
