package com.project.bookreader.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
//import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.project.bookreader.data.model.Book
import com.project.bookreader.presentation.components.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    book: Book?,
    onNavigateBack: () -> Unit,
    onPreviewClick: (String) -> Unit
) {
    var showPreviewDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(book?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = book?.thumbnail,
                    contentDescription = book?.title,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(2f/3f),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = book?.title ?: "",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "By ${book?.authors?.joinToString(", ") ?: ""}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (!book?.categories.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Categories: ${book?.categories?.joinToString(", ") ?: ""}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                book?.publishedDate?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Published: $it",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                book?.pageCount?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Pages: $it",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showPreviewDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Filled.List,
                        contentDescription = "Preview",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Read Preview")
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = book?.description ?: "No description available",
                    style = MaterialTheme.typography.bodyMedium
                )

                if (showPreviewDialog) {
                    AlertDialog(
                        onDismissRequest = { showPreviewDialog = false },
                        title = { Text("Preview Book") },
                        text = { 
                            Text(
                                "Would you like to read a preview of '${book?.title ?: ""}'?",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showPreviewDialog = false
                                    onPreviewClick(book?.previewLink ?: "")
                                }
                            ) {
                                Text("Read")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showPreviewDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}
