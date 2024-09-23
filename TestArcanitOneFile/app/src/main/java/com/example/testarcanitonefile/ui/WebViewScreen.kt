package com.example.testarcanitonefile.ui

import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

@Composable
fun WebViewScreen(url: String, navController: NavHostController) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf<String?>(null) }

    val webView = remember { WebView(context).apply { settings.javaScriptEnabled = true } }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Просмотр файла") },
            navigationIcon = {
                IconButton(onClick = {
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        navController.popBackStack()
                    }
                }) {
                    Icon(
                        androidx.compose.material.icons.Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            backgroundColor = Color(0xFF6200EE),
            contentColor = Color.White,
            elevation = 4.dp
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (url.endsWith(".pdf", true)) {
                LaunchedEffect(Unit) {
                    val pdfIntent = Intent(Intent.ACTION_VIEW)
                    pdfIntent.setDataAndType(Uri.parse(url), "application/pdf")
                    pdfIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                    try {
                        context.startActivity(pdfIntent)
                        isLoading = false
                    } catch (e: Exception) {
                        loadError = "Нет доступных приложений для открытия PDF"
                        isLoading = false
                    }
                }
            } else {
                AndroidView(factory = {
                    webView.apply {
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                isLoading = false
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                loadError = "Ошибка загрузки страницы: формат не поддерживается"
                                isLoading = false
                            }
                        }
                        loadUrl(url)
                    }
                }, update = {
                    webView.loadUrl(url)
                })
            }

            if (isLoading && loadError == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            loadError?.let {
                isLoading = false
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = it, color = Color.Red, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}
