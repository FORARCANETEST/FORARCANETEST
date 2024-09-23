package com.example.testarcanitonefile.ui


import android.util.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.unit.*
import com.example.testarcanitonefile.data.*

@Composable
fun RepositoryContentItem(content: RepositoryContent, onClick: () -> Unit) {
    val icon: ImageVector = if (content.type == "dir") {
        androidx.compose.material.icons.Icons.Default.Folder
    } else {
        androidx.compose.material.icons.Icons.Default.InsertDriveFile
    }

    val iconTint = if (content.type == "dir") {
        Color.Yellow
    } else {
        Color.Gray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                if (content.type == "file") {
                    content.download_url?.let { url ->
                        if (url.isNotEmpty()) {
                            Log.d("RepositoryContentItem", "Opening URL: $url")
                            onClick()
                        } else {
                            Log.e(
                                "RepositoryContentItem",
                                "download_url is empty for file: ${content.name}"
                            )
                        }
                    } ?: run {
                        Log.e(
                            "RepositoryContentItem",
                            "download_url is null for file: ${content.name}"
                        )
                    }
                } else {
                    onClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = content.name)
    }
}
