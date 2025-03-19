package com.glory.maliyako.navigation

sealed class Routes(val route: String) {
    object ProductList : Routes("product_list")
    object AddProduct : Routes("add_product")
    object Location : Routes("location")
    object EditProduct : Routes("edit_product/{productId}") {
        fun createRoute(productId: Int) = "edit_product/$productId"
    }
}