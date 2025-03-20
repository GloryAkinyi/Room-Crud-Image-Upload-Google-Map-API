package com.glory.maliyako.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.glory.maliyako.viewmodel.LocationViewModel

@Composable
fun LocationScreen(navController: NavController, viewModel: LocationViewModel = viewModel()) {
    val context = LocalContext.current
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        )
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasLocationPermission = granted
            if (granted) viewModel.fetchCurrentLocation()
        }
    )

    // Request permission only if not already granted
    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            viewModel.fetchCurrentLocation()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (hasLocationPermission) {
            LocationMap(viewModel)
        } else {
            Button(onClick = { locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }) {
                Text("Grant Location Permission")
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun LocationMap(viewModel: LocationViewModel) {
    val location by viewModel.currentLocation.collectAsState(initial = null)
    val defaultLocation = LatLng(-1.286389, 36.817223) // Default to Nairobi, Kenya

    val currentLatLng = location?.let { LatLng(it.latitude, it.longitude) } ?: defaultLocation
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLatLng, 15f)
    }

    LaunchedEffect(location) {
        location?.let {
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = currentLatLng),
            title = "Current Location"
        )
    }
}
