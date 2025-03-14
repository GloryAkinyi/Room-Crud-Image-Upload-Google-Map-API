package com.glory.maliyako.ui.screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
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
                // ✅ Product Image
                Image(
                    painter = rememberAsyncImagePainter(model = Uri.parse(product.imagePath)),
                    contentDescription = "Product Image",
                    modifier = Modifier.size(60.dp) // Adjust size as needed
                )

                Spacer(modifier = Modifier.width(8.dp))

                // ✅ Product Name & Price
                Text(
                    text = "${product.name} - $${product.price}",
                    modifier = Modifier.weight(1f)
                )

                // ✅ Delete Button
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
