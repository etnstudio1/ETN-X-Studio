package com.etnstudio.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.etnstudio.user.presentation.library.LibraryScreen
import com.etnstudio.user.presentation.player.PlayerScreen
import com.etnstudio.user.presentation.search.SearchScreen
import com.etnstudio.user.ui.theme.ETNStudioUserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ETNStudioUserTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "library") {
                        composable("library") { LibraryScreen(navController) }
                        composable("search") { SearchScreen(navController) }
                        composable("player/{itemId}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("itemId") ?: ""
                            PlayerScreen(id)
                        }
                    }
                }
            }
        }
    }
}
