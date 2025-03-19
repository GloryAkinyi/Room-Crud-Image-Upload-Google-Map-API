package com.glory.maliyako.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.glory.maliyako.ui.screen.*
import com.glory.maliyako.viewmodel.LocationViewModel
import com.glory.maliyako.viewmodel.ProductViewModel

@Composable
fun AppNavHost(navController: NavHostController, productViewModel: ProductViewModel) {
    val locationViewModel: LocationViewModel = viewModel() // ✅ Correct ViewModel for LocationScreen

    NavHost(navController = navController, startDestination = Routes.ProductList.route) {
        composable(Routes.ProductList.route) {
            ProductListScreen(navController, productViewModel)
        }
        composable(Routes.AddProduct.route) {
            AddProductScreen(navController, productViewModel)
        }
        composable(Routes.Location.route) {
            LocationScreen(navController, locationViewModel) // ✅ Correct ViewModel passed
        }
        composable(Routes.EditProduct.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            if (productId != null) {
                EditProductScreen(productId, navController, productViewModel)
            }
        }
    }
}
