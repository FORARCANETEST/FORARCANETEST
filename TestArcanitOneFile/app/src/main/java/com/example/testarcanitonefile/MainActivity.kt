package com.example.testarcanitonefile

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.material.*
import com.example.testarcanitonefile.ui.*
import com.example.testarcanitonefile.viewmodels.*

class MainActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppNavigation(viewModel = searchViewModel)
                }
            }
        }
    }
}