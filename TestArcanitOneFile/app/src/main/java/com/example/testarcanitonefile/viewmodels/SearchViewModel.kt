package com.example.testarcanitonefile.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.testarcanitonefile.data.*
import kotlinx.coroutines.*

class SearchViewModel : ViewModel() {
    var searchResults = mutableStateOf<List<Any>>(emptyList())
    var errorMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)

    private var searchJob: Job? = null

    fun search(query: String) {
        if (query.isBlank()) {
            errorMessage.value = "Поле поиска не может быть пустым"
            return
        }

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(500)
            executeSearch(query)
        }
    }

    private suspend fun executeSearch(query: String) {
        isLoading.value = true
        errorMessage.value = null
        try {
            val usersDeferred =
                viewModelScope.async { RetrofitInstance.api.searchUsers(query).items }
            val reposDeferred =
                viewModelScope.async { RetrofitInstance.api.searchRepositories(query).items }

            val users = usersDeferred.await()
            val repos = reposDeferred.await()

            if (users.isEmpty() && repos.isEmpty()) {
                errorMessage.value = "Совпадений не найдено"
            } else {
                searchResults.value = (users + repos).sortedBy { item ->
                    when (item) {
                        is GitHubUser -> item.login
                        is GitHubRepository -> item.name
                        else -> ""
                    }
                }
            }
        } catch (e: Exception) {
            errorMessage.value = "Произошла ошибка: ${e.localizedMessage}"
        } finally {
            isLoading.value = false
        }
    }
}
