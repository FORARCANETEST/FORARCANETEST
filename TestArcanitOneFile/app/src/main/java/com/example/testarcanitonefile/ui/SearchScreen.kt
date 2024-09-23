package com.example.testarcanitonefile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.example.testarcanitonefile.data.*
import com.example.testarcanitonefile.viewmodels.*

@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavHostController) {
    var query by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Поиск") },
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    if (!viewModel.isLoading.value && query.length >= 3) {
                        viewModel.search(query)
                        keyboardController?.hide()
                    }
                },
                enabled = query.length >= 3 && !viewModel.isLoading.value
            ) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            viewModel.errorMessage.value?.let { error ->
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = error,
                        color = androidx.compose.ui.graphics.Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )

                    Button(onClick = {
                        viewModel.search(query)
                    }) {
                        Text(text = "Повторить")
                    }
                }
            }

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (!viewModel.isLoading.value && viewModel.errorMessage.value == null) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(viewModel.searchResults.value) { item ->
                        when (item) {
                            is GitHubUser -> UserCard(user = item, navController = navController)
                            is GitHubRepository -> RepoCard(
                                repo = item,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
