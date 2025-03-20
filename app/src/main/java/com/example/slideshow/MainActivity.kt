// MainActivity.kt
package com.example.slideshow

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SlideshowApp()
            }
        }
    }
}

@Composable
fun SlideshowApp() {
    // Image resources
    val imageResources = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5
    )
    // Captions
    val imageCaptions = listOf(
        "Abstract floral art",
        "Morning sun over blue lake",
        "River flowing through mountain range",
        "Waterfall in the forest",
        "Cave on by the sea"
    )

    // State handling

    // State to track the current image index. Recompose when initial value 0 changes
    var currentIndex by remember { mutableIntStateOf(0) }

    // State to track the input text. Recompose when initial value "1" changes
    var inputText by remember { mutableStateOf(TextFieldValue("1")) }


    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Box to show the image
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imageResources[currentIndex]),
                contentDescription = "Image ${currentIndex + 1}",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Caption Under Box with Image
        Text(
            text = imageCaptions[currentIndex],
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
        )

        // Image number
        Text(
            text = "${currentIndex + 1}/${imageResources.size}",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Row of navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Back button
            Button(
                onClick = {
                    if (currentIndex > 0) {
                        currentIndex -= 1
                    } else {
                        currentIndex = imageResources.size - 1  // Wrap around to the last image Where imageResources.size is the total number of images
                    }
                    inputText = TextFieldValue((currentIndex + 1).toString()) // recomposes inputText
                }
            ) {
                Text("Previous")
            }

            // Next button
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
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                },
                label = { Text("Image number") },
                modifier = Modifier.weight(2f),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    val requestedIndex = inputText.text.toInt()

                    if (requestedIndex in 1..imageResources.size) {
                        currentIndex = requestedIndex - 1  // Convert to 0-based index
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Go")
            }
        }
    }
}