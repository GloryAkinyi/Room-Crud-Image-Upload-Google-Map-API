package com.glory.maliyako.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.glory.maliyako.viewmodel.ProductViewModel

@Composable
fun AddProductScreen(navController: NavController, viewModel: ProductViewModel) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var imagePath by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Product Name") })
        TextField(value = price, onValueChange = { price = it }, label = { Text("Product Price") })
        TextField(value = imagePath, onValueChange = { imagePath = it }, label = { Text("Image Path") })

        Button(onClick = {
            val priceValue = price.toDoubleOrNull()
            if (priceValue != null) {
                viewModel.addProduct(name, priceValue, imagePath)
                navController.popBackStack()
            }
        }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Add Product")
        }
    }
}
