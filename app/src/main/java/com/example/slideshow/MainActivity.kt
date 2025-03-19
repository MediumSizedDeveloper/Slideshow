// MainActivity.kt
package com.example.imagegallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.slideshow.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ImageGalleryApp()
            }
        }
    }
}

@Composable
fun ImageGalleryApp() {
    // Image resources and captions
    val imageResources = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5
    )

    val imageCaptions = listOf(
        "Beautiful mountain landscape",
        "Sunset over the ocean",
        "City skyline at night",
        "Autumn forest trail",
        "Desert sand dunes"
    )

    // State
    var currentIndex by remember { mutableStateOf(0) }
    var inputText by remember { mutableStateOf(TextFieldValue("1")) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imageResources[currentIndex]),
                contentDescription = "Image ${currentIndex + 1}",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Caption
        Text(
            text = imageCaptions[currentIndex],
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )

        // Counter
        Text(
            text = "${currentIndex + 1}/${imageResources.size}",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    currentIndex = if (currentIndex > 0) {
                        currentIndex - 1
                    } else {
                        imageResources.size - 1  // Wrap around to the last image
                    }
                    inputText = TextFieldValue((currentIndex + 1).toString())
                }
            ) {
                Text("Previous")
            }

            Button(
                onClick = {
                    currentIndex = if (currentIndex < imageResources.size - 1) {
                        currentIndex + 1
                    } else {
                        0  // Wrap around to the first image
                    }
                    inputText = TextFieldValue((currentIndex + 1).toString())
                }
            ) {
                Text("Next")
            }
        }

        // Jump to specific image
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    showError = false
                },
                label = { Text("Image number") },
                modifier = Modifier.weight(2f),
                isError = showError,
                supportingText = if (showError) {
                    { Text(text = errorMessage) }
                } else null,
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    try {
                        val requestedIndex = inputText.text.toInt()

                        if (requestedIndex in 1..imageResources.size) {
                            currentIndex = requestedIndex - 1  // Convert to 0-based index
                        } else {
                            showError = true
                            errorMessage = "Enter 1-${imageResources.size}"
                        }
                    } catch (e: NumberFormatException) {
                        showError = true
                        errorMessage = "Enter a valid number"
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Go")
            }
        }
    }
}