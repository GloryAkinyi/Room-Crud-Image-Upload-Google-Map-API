package com.glory.maliyako.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.glory.maliyako.model.Product
import com.glory.maliyako.viewmodel.ProductViewModel

@Composable
fun EditProductScreen(productId: Int, navController: NavController, viewModel: ProductViewModel) {
    val productList by viewModel.allProducts.observeAsState(emptyList())
    val product = remember(productList) { productList.find { it.id == productId } }

    var name by remember { mutableStateOf(product?.name ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var imagePath by remember { mutableStateOf(product?.imagePath ?: "") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Edit Product", style = MaterialTheme.typography.headlineSmall)

        if (product != null) {
            TextField(value = name, onValueChange = { name = it }, label = { Text("Product Name") })
            TextField(value = price, onValueChange = { price = it }, label = { Text("Product Price") })
            TextField(value = imagePath, onValueChange = { imagePath = it }, label = { Text("Image Path") })

            Button(
                onClick = {
                    val updatedPrice = price.toDoubleOrNull()
                    if (updatedPrice != null) {
                        viewModel.updateProduct(product.copy(name = name, price = updatedPrice, imagePath = imagePath))
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Update Product")
            }
        } else {
            Text(text = "Product not found", color = MaterialTheme.colorScheme.error)
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
    }
}
