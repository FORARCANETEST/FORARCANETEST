package com.example.testarcanitonefile.ui

import android.content.*
import android.net.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import coil.compose.*
import com.example.testarcanitonefile.data.*

@Composable
fun UserCard(user: GitHubUser, navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(user.html_url))
                navController.context.startActivity(intent)
            },
        color = Color.White,
        shape = MaterialTheme.shapes.medium,
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberImagePainter(
                data = user.avatar_url ?: "",
                builder = {
                    crossfade(true)
                    error(android.R.drawable.ic_menu_report_image)
                    placeholder(android.R.drawable.ic_menu_report_image)
                }
            )

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )

            Text(
                text = user.login,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )

            Text(
                text = "%.2f".format(user.score),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color(0xFFFFA500)
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
            )
        }
    }
}