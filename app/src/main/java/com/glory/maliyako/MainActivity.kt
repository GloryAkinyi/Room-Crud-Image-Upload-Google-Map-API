package com.glory.maliyako

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.glory.maliyako.navigation.AppNavHost
import com.glory.maliyako.viewmodel.ProductViewModel
import com.glory.maliyako.viewmodel.ProductViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Create ViewModel using Factory
        val viewModelFactory = ProductViewModelFactory()
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)

        setContent {
            val navController = rememberNavController() // ✅ Create navController in Compose

            androidx.compose.material3.MaterialTheme {
                AppNavHost(navController, viewModel) // ✅ Pass correctly
            }
        }
    }
}
