package com.project.bookreader.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val description: String?,
    val thumbnail: String?,
    val previewLink: String,
    val categories: List<String>?,
    val pageCount: Int?,
    val averageRating: Double?,
    val publishedDate: String?
) : Parcelable

data class BookResponse(
    val items: List<BookItem>,
    val totalItems: Int
)

data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val previewLink: String?,
    val categories: List<String>?,
    val pageCount: Int?,
    val averageRating: Double?,
    val publishedDate: String?
)

data class ImageLinks(
    val thumbnail: String?
)
