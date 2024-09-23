package com.example.testarcanitonefile.ui


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.example.testarcanitonefile.data.*

@Composable
fun RepoCard(repo: GitHubRepository, navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("repositoryContent/${repo.owner.login}/${repo.name}")
            },
        color = Color.White,
        shape = MaterialTheme.shapes.medium,
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = repo.name,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Top),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = repo.forks_count.toString(),
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Forks",
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    )
                }
            }

            repo.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body2.copy(
                        color = Color.Gray
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
