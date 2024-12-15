package com.project.bookreader.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object BookDetail : Screen("book_detail/{bookId}") {
        fun createRoute(bookId: String) = "book_detail/$bookId"
    }
    object CategoryBooks : Screen("category/{category}") {
        fun createRoute(category: String) = "category/$category"
    }
}
