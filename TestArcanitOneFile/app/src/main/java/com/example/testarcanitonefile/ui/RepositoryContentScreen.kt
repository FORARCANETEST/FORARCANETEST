package com.example.testarcanitonefile.ui

import android.net.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import com.example.testarcanitonefile.viewmodels.*

@Composable
fun RepositoryContentScreen(owner: String, repo: String, navController: NavHostController) {
    val viewModel: RepositoryContentViewModel = viewModel()

    LaunchedEffect(viewModel.currentPath) {
        viewModel.loadContent(owner, repo, viewModel.currentPath)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(text = if (viewModel.currentPath.isEmpty()) repo else viewModel.currentPath)
            },
            navigationIcon = {
                IconButton(onClick = {
                    if (viewModel.currentPath.isEmpty()) {
                        navController.popBackStack()
                    } else {
                        val lastSlashIndex = viewModel.currentPath.lastIndexOf('/')
                        viewModel.currentPath = if (lastSlashIndex != -1) {
                            viewModel.currentPath.substring(0, lastSlashIndex)
                        } else {
                            ""
                        }
                        viewModel.loadContent(owner, repo, viewModel.currentPath)
                    }
                }) {
                    Icon(
                        androidx.compose.material.icons.Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    viewModel.currentPath = ""
                    viewModel.loadContent(owner, repo, viewModel.currentPath)
                }) {
                    Icon(
                        androidx.compose.material.icons.Icons.Default.Home,
                        contentDescription = "Home"
                    )
                }
            }
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                viewModel.errorMessage.value?.let { error ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = error,
                            color = androidx.compose.ui.graphics.Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(onClick = { viewModel.loadContent(owner, repo, viewModel.currentPath) }) {
                            Text("Повторить")
                        }
                    }
                } ?: run {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(viewModel.contentList.value) { content ->
                            RepositoryContentItem(
                                content = content,
                                onClick = {
                                    if (content.type == "file") {
                                        val encodedUrl = Uri.encode(content.download_url)
                                        navController.navigate(route = "webview/$encodedUrl")
                                    } else {
                                        viewModel.currentPath =
                                            if (viewModel.currentPath.isEmpty()) content.name else "${viewModel.currentPath}/${content.name}"
                                        viewModel.loadContent(owner, repo, viewModel.currentPath)
                                    }
                                }
                            )
                            Divider(
                                color = androidx.compose.ui.graphics.Color.Gray,
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    }
}
