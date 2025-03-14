package com.glory.maliyako.ui.screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.glory.maliyako.viewmodel.ProductViewModel

@Composable
fun AddProductScreen(navController: NavController, viewModel: ProductViewModel) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Image Picker Launcher
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            Log.d("ImagePicker", "Selected image URI: $it")
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Product Name") })
        TextField(value = price, onValueChange = { price = it }, label = { Text("Product Price") })

        // Image Picker Box
        Box(
            modifier = Modifier
                .size(150.dp)
                .clickable { imagePicker.launch("image/*") }
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = "Selected Image"
                )
            } else {
                Text("Tap to pick image")
            }
        }

        Button(onClick = {
            val priceValue = price.toDoubleOrNull()
            if (priceValue != null) {
                imageUri?.toString()?.let { viewModel.addProduct(name, priceValue, it) } // Save URI as String
                navController.popBackStack()
            }
        }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Add Product")
        }
    }
}
