package com.glory.maliyako.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.glory.maliyako.ui.screen.*
import com.glory.maliyako.viewmodel.LocationViewModel
import com.glory.maliyako.viewmodel.ProductViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_PRODUCT_LIST,
    productViewModel: ProductViewModel = viewModel(),
    locationViewModel: LocationViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_PRODUCT_LIST) {
            ProductListScreen(navController, productViewModel)
        }
        composable(ROUT_ADD_PRODUCT) {
            AddProductScreen(navController, productViewModel)
        }
        composable(ROUT_LOCATION) {
            LocationScreen(navController, locationViewModel)
        }
        composable(
            route = ROUT_EDIT_PRODUCT,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            if (productId != null) {
                EditProductScreen(productId, navController, productViewModel)
            }
        }

    }
}
