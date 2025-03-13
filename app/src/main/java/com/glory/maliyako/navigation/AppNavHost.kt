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
        composable(
            route = Routes.EditProduct.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            EditProductScreen(productId, navController, viewModel) // ✅ Pass correctly
        }
    }
}
