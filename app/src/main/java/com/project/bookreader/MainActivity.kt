package com.project.bookreader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.bookreader.data.model.Book
import com.project.bookreader.presentation.navigation.Screen
import com.project.bookreader.presentation.screens.*
import com.project.bookreader.presentation.viewmodel.BookDetailViewModel
import com.project.bookreader.ui.theme.BookReaderTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookReaderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val bookDetailViewModel: BookDetailViewModel = hiltViewModel()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(
                                onNavigateToSearch = {
                                    navController.navigate(Screen.Search.route)
                                },
                                onNavigateToBookDetail = { book ->
                                    bookDetailViewModel.setBook(book)
                                    navController.navigate(Screen.BookDetail.createRoute(book.id)) {
                                        launchSingleTop = true
                                    }
                                },
                                onNavigateToCategory = { category ->
                                    navController.navigate(Screen.CategoryBooks.createRoute(category))
                                }
                            )
                        }

                        composable(Screen.Search.route) {
                            SearchScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                },
                                onNavigateToBookDetail = { book ->
                                    bookDetailViewModel.setBook(book)
                                    navController.navigate(Screen.BookDetail.createRoute(book.id)) {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }

                        composable(
                            route = Screen.BookDetail.route,
                            arguments = listOf(
                                navArgument("bookId") { type = NavType.StringType }
                            )
                        ) {
                            val bookId = it.arguments?.getString("bookId")
                            val book = bookDetailViewModel.getBookById(bookId)
                            book?.let { bookData ->
                                BookDetailScreen(
                                    book = bookData,
                                    onNavigateBack = {
                                        navController.popBackStack()
                                    },
                                    onPreviewClick = { previewLink ->
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(previewLink))
                                        startActivity(intent)
                                    }
                                )
                            }
                        }

                        composable(
                            route = Screen.CategoryBooks.route,
                            arguments = listOf(
                                navArgument("category") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val category = backStackEntry.arguments?.getString("category") ?: return@composable
                            CategoryBooksScreen(
                                category = category,
                                onNavigateBack = {
                                    navController.popBackStack()
                                },
                                onNavigateToBookDetail = { book ->
                                    bookDetailViewModel.setBook(book)
                                    navController.navigate(Screen.BookDetail.createRoute(book.id)) {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}