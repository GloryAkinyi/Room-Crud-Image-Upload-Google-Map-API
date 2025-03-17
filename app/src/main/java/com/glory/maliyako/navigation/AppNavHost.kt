package com.glory.maliyako.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.glory.maliyako.ui.screen.*
import com.glory.maliyako.viewmodel.ProductViewModel

@Composable
fun AppNavHost(navController: NavHostController, viewModel: ProductViewModel) { // ✅ FIXED: Accept navController
    NavHost(navController = navController, startDestination = Routes.ProductList.route) {
        composable(Routes.ProductList.route) {
            ProductListScreen(navController, viewModel) // ✅ Pass correctly
        }
        composable(Routes.AddProduct.route) {
            AddProductScreen(navController, viewModel) // ✅ Pass correctly
        }
        // Add EditProductScreen and extract productId from the argument
        composable(Routes.EditProduct.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            if (productId != null) {
                EditProductScreen(productId, navController, viewModel)
            }
        }
    }
}
