package com.example.testarcanitonefile.ui

import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.testarcanitonefile.viewmodels.*

@Composable
fun AppNavigation(viewModel: SearchViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            SearchScreen(viewModel = viewModel, navController = navController)
        }
        composable("repositoryContent/{owner}/{repo}") { backStackEntry ->
            val owner = backStackEntry.arguments?.getString("owner") ?: ""
            val repo = backStackEntry.arguments?.getString("repo") ?: ""
            RepositoryContentScreen(owner, repo, navController)
        }
        composable("webview/{url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(url = url, navController)
        }
    }
}
