package com.glory.maliyako.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.glory.maliyako.viewmodel.ProductViewModel
import com.glory.maliyako.navigation.Routes
import com.glory.maliyako.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavController, viewModel: ProductViewModel) {
    val productList by viewModel.allProducts.observeAsState(emptyList())
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Products", fontSize = 20.sp) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(Color.LightGray),
                actions = {

                    //Menu with navigation
                    IconButton(onClick = { showMenu = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Product List") },
                            onClick = {
                                navController.navigate(Routes.ProductList.route)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Add Product") },
                            onClick = {
                                navController.navigate(Routes.AddProduct.route)
                                showMenu = false
                            }
                        )
                    }
                    //End of menu with navigation
                }
            )
        },
        bottomBar = { BottomNavigationBar1(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(productList.size) { index ->
                    ProductItem(navController, productList[index], viewModel)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}



@Composable
fun ProductItem(navController: NavController, product: Product, viewModel: ProductViewModel) {
    val painter: Painter = rememberAsyncImagePainter(
        model = product.imagePath?.let { Uri.parse(it) } ?: Uri.EMPTY
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                if (product.id != 0) {
                    navController.navigate(Routes.EditProduct.createRoute(product.id))
                }
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Product Image
            Image(
                painter = painter,
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Full card height
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay at the bottom for readability
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp) // Height of the gradient overlay
                    .align(Alignment.BottomStart) // Align to the bottom
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = 0f, // Start from the top of the Box
                            endY = Float.POSITIVE_INFINITY // End at the bottom
                        )
                    )
            )

            // Product Info (Name and Price) positioned just above the buttons
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart) // Align to the bottom-left
                    .padding(start = 12.dp, bottom = 60.dp) // Adjust padding to position above buttons
            ) {
                Text(
                    text = product.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Price: Ksh${product.price}",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            // Buttons (Message Seller, Edit, Delete) positioned at the bottom-right
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.BottomEnd) // Align to the bottom-right
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val mContext = LocalContext.current
                    OutlinedButton(
                        onClick = {
                            val smsIntent = Intent(Intent.ACTION_SENDTO)
                            smsIntent.data = "smsto:${product.phone}".toUri()
                            smsIntent.putExtra("sms_body", "Hello Seller,...?")
                            mContext.startActivity(smsIntent)
                        },
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Message Seller"
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "Message Seller")
                        }
                    }

                    IconButton(
                        onClick = {
                            navController.navigate(Routes.EditProduct.createRoute(product.id))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = { viewModel.deleteProduct(product) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

// Bottom Navigation Bar Component
@Composable
fun BottomNavigationBar1(navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFFA2B9A2),
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.ProductList.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Product List") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.AddProduct.route) },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Add Product") },
            label = { Text("Add") }
        )
    }
}
