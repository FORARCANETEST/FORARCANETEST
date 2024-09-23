package com.example.testarcanitonefile.viewmodels


import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.testarcanitonefile.data.*
import kotlinx.coroutines.*

class RepositoryContentViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var contentList = mutableStateOf<List<RepositoryContent>>(emptyList())
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)

    var currentPath: String
        get() = savedStateHandle.get<String>("currentPath") ?: ""
        set(value) {
            savedStateHandle.set("currentPath", value)
        }

    fun loadContent(owner: String, repo: String, path: String = "") {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                val contents = RetrofitInstance.api.getRepositoryContents(owner, repo, path)
                contentList.value = contents
            } catch (e: Exception) {
                errorMessage.value = "Произошла ошибка: ${e.localizedMessage}"
            } finally {
                isLoading.value = false
            }
        }
    }
}
