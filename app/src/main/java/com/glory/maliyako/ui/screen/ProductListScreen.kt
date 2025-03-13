package com.glory.maliyako.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.glory.maliyako.viewmodel.ProductViewModel
import com.glory.maliyako.navigation.Routes

@Composable
fun ProductListScreen(navController: NavController, viewModel: ProductViewModel) {
    val productList by viewModel.allProducts.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        productList.forEach { product ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(Routes.EditProduct.route + "/${product.id}") // ✅ Navigate to EditProductScreen
                    }
            ) {
                Text(text = "${product.name} - $${product.price}", modifier = Modifier.weight(1f))

                Button(onClick = { viewModel.deleteProduct(product) }) {
                    Text("Delete")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate(Routes.AddProduct.route) }) {
            Text("Add Product")
        }
    }
}
