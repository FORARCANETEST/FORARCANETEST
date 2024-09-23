package com.example.testarcanitonefile.data

data class GitHubUser(
    val login: String,
    val avatar_url: String?,
    val score: Float,
    val html_url: String
)

data class GitHubRepository(
    val name: String,
    val owner: GitHubUser,
    val forks_count: Int,
    val description: String?,
    val score: Float
)

data class RepositoryContent(
    val name: String,
    val path: String,
    val type: String,
    val download_url: String?
)
