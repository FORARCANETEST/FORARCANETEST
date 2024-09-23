package com.example.testarcanitonefile.data

import retrofit2.http.*

interface GitHubApiService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): SearchUsersResponse

    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): SearchRepositoriesResponse

    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getRepositoryContents(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String = ""
    ): List<RepositoryContent>
}

data class SearchUsersResponse(val items: List<GitHubUser>)
data class SearchRepositoriesResponse(val items: List<GitHubRepository>)
