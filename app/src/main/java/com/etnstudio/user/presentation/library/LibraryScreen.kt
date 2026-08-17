package com.etnstudio.user.presentation.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.etnstudio.user.data.models.ItemType
import com.etnstudio.user.data.models.MediaItem

@Composable
fun LibraryScreen(navController: NavController, viewModel: LibraryViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ETN X Studio") },
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { padding ->
        when (uiState) {
            is LibraryUiState.Loading -> Box(modifier = Modifier.padding(padding)) { Text("Loading...") }
            is LibraryUiState.Success -> {
                val items = (uiState as LibraryUiState.Success).items
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(items) { item ->
                        LibraryCard(item, onClick = {
                            if (item.type == ItemType.FOLDER) {
                                // navigate to folder contents (simplified)
                            } else {
                                navController.navigate("player/${item.id}")
                            }
                        })
                    }
                }
            }
            is LibraryUiState.Error -> Text("Error: ${(uiState as LibraryUiState.Error).message}")
        }
    }
}

@Composable
fun LibraryCard(item: MediaItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(item.name)
            if (item.isLocked) Text(" 🔒")
        }
    }
}

sealed class LibraryUiState {
    object Loading : LibraryUiState()
    data class Success(val items: List<MediaItem>) : LibraryUiState()
    data class Error(val message: String) : LibraryUiState()
}
