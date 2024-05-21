package com.example.myapplication

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil.compose.rememberImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val painter = painterResource(id = R.drawable.b1)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                ImageCard(painter = painter, contentDescription = ".")
            }

            App()
        }
    }
}


@Composable
fun App(){
    val navController= rememberNavController()
    var isImageUploaded by remember { mutableStateOf(false) }
    var imageUri: Uri? = null

    NavHost(navController = navController,
        startDestination ="LandingScreen"  ){

        composable(route="EditingScreen"){
            EditingScreen(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="Brightness"){
            Brightness(navController, imageUri ?: Uri.EMPTY)
        }

        //composable(route="Contrast"){
        //    Contrast(navController, imageUri ?: Uri.EMPTY)
      //  }

        composable(route="Saturation"){
            Saturation(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route = "Contrast") {
            BlurScreen(navController = navController, imageUri = imageUri)
        }


        composable(route="Clone"){
            Clone(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="Pen"){
            Pen(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="Filters"){
            Filters(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="Selection"){
            Selection(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="Cropping"){
            Cropping(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="Foreground CG"){
            FCG(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="Background CG"){
            BCG(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="Advanced"){
            Advanced(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="CropSelection"){
            CropSelection(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="Basic"){
            Basic(navController, imageUri ?: Uri.EMPTY)
        }

        composable(route="LandingScreen"){
            var showStudioFont by remember { mutableStateOf(true) }
            StudioFont()

            UploadImage(onImageUploaded = { uri ->
                imageUri = uri
                isImageUploaded = true
            })
            if (isImageUploaded) {
                NextButton(navController)
            }
        }
    }
}

@Composable
fun BlurScreen(navController: NavController, imageUri: Uri?) {
    val painter = rememberImagePainter(
        data = imageUri ?: Uri.EMPTY,
        builder = {
            // Optionally, you can apply transformations here if needed
        }
    )

    Blur(navController = navController, imagePainter = painter)
}

@Composable
fun Blur(navController: NavController, imagePainter: Painter) {
    var blurRadius by remember { mutableStateOf(0f) }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(700.dp)
                .padding(20.dp)
        ){
            Image(
                painter = imagePainter,
                contentDescription = "Blurry Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier

                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(650.dp)

                    .blur(
                        radiusX = blurRadius.dp,
                        radiusY = 10.dp,
                        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))


                    )
                //    .graphicsLayer {
                        // Apply blur effect here
                //        alpha = 1f - (blurRadius / 25f) // Adjust the alpha value based on blurRadius
                //    }

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = blurRadius,
            onValueChange = { newBlurRadius ->
                // Update the blur radius
                blurRadius = newBlurRadius
            },
            valueRange = 0f..25f,
            steps = 100,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@SuppressLint("RememberReturnType")





@Composable
fun Brightness(navController: NavController, imageUri: Uri) {
    var brightness by remember { mutableStateOf(2.5f) } // Adjust brightness value as needed

    val imagePainter = rememberAsyncImagePainter(imageUri)

    val colorFilter = remember(brightness) {
        ColorFilter.colorMatrix(ColorMatrix().apply {
            setToScale(brightness, brightness, brightness, 1f) // Apply brightness
        })
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(700.dp)
                .padding(20.dp)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                colorFilter = colorFilter,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(650.dp)
            )
        }

        Slider(
            value = brightness,
            onValueChange = { newBrightness ->
                brightness = newBrightness
            },
            valueRange = 0f..6f, // Adjust the range as needed
            steps = 50,
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        )
    }
}

@Composable
fun Saturation(navController: NavController, imageUri: Uri) {
    var saturation by remember { mutableStateOf(1.0f) } // Adjust saturation value as needed

    val imagePainter = rememberAsyncImagePainter(imageUri)

    val colorMatrix = remember(saturation) {
        val matrix = ColorMatrix()
        val lumR = 0.299f
        val lumG = 0.587f
        val lumB = 0.114f

        val sr = (1 - saturation) * lumR
        val sg = (1 - saturation) * lumG
        val sb = (1 - saturation) * lumB

        matrix.setToScale(sr + saturation, sg, sb, 1f)

        matrix
    }

    val colorFilter = remember(colorMatrix) {
        ColorFilter.colorMatrix(colorMatrix)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(700.dp)
                .padding(20.dp)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                colorFilter = colorFilter,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(650.dp)
            )
        }

        Slider(
            value = saturation,
            onValueChange = { newSaturation ->
                saturation = newSaturation
            },
            valueRange = 0f..2f, // Adjust the range as needed
            steps = 100,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun Basic(navController: NavController, imageUri: Uri){
    val merriFont = FontFamily(Font(R.font.merri, FontWeight.Normal))
    val exp = painterResource(R.drawable.exp)
    val contrast = painterResource(R.drawable.contrast)
    val saturation = painterResource(R.drawable.saturation)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(550.dp)
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.weight(0.2f))
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
        ) {


            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = exp,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Brightness") }
                        )
                        Text(
                            text = "Exposure",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }


            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = contrast,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Contrast") }
                        )
                        Text(
                            text = "Contrast",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = saturation,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Saturation") }
                        )
                        Text(
                            text = "Saturation",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Advanced(navController: NavController, imageUri: Uri){
    val merriFont = FontFamily(Font(R.font.merri, FontWeight.Normal))
    val pen = painterResource(R.drawable.pen)
    val clone = painterResource(R.drawable.clone)


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(550.dp)
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.weight(0.2f))
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
        ) {

            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = clone,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Clone") }
                        )
                        Text(
                            text = "Clone",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = pen,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Pen") }
                        )
                        Text(
                            text = "Pen",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }
        }
    }
}




@Composable
fun Clone(navController: NavController, imageUri: Uri){
    Text(text = "Clone",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.clickable { navController.navigate(route = "EditingScreen") })

}


@Composable
fun Selection(navController: NavController, imageUri: Uri){
    Text(text = "Selection",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.clickable { navController.navigate(route = "EditingScreen") })

}


@Composable
fun CropSelection(navController: NavController, imageUri: Uri)
{
    val merriFont = FontFamily(Font(R.font.merri, FontWeight.Normal))
    val crop = painterResource(R.drawable.crop)
    val select = painterResource(R.drawable.select)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(550.dp)
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.weight(0.2f))
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
        ) {

            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = crop,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Cropping") }
                        )
                        Text(
                            text = "Crop",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = select,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Selection") }
                        )
                        Text(
                            text = "Select",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Pen(navController: NavController, imageUri: Uri){
    Text(text = "Pen",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.clickable { navController.navigate(route = "EditingScreen") })

}


@Composable
fun Filters(navController: NavController, imageUri: Uri){
    Text(text = "Filters",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.clickable { navController.navigate(route = "EditingScreen") })

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(550.dp)
            )
        }
    }


}


@Composable
fun FCG(navController: NavController, imageUri: Uri){
    Text(text = "FCG",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.clickable { navController.navigate(route = "EditingScreen") })

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(550.dp)
            )
        }
    }
}


@Composable
fun BCG(navController: NavController, imageUri: Uri){
    Text(text = "BCG",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.clickable { navController.navigate(route = "EditingScreen") })
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(550.dp)
            )
        }
    }
}


@Composable
fun Cropping(navController: NavController, imageUri: Uri){
    Text(text = "Cropping",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.clickable { navController.navigate(route = "EditingScreen") })

}


@Composable
fun EditingScreen(navController: NavController, imageUri: Uri) {
    val merriFont = FontFamily(Font(R.font.merri, FontWeight.Normal))
    val bright = painterResource(R.drawable.brightness)
    val crop = painterResource(R.drawable.crop)
    val fgc = painterResource(R.drawable.fgc)
    val bgc = painterResource(R.drawable.bgc)
    val adv = painterResource(R.drawable.advanced)
    val filter = painterResource(R.drawable.filters)


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(550.dp)
            )
        }
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.weight(0.2f))
        LazyRow(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(18.dp)
        ) {

            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = bright,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Basic") }
                        )
                        Text(
                            text = "Light",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }


            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = filter,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Filters") }
                        )
                        Text(
                            text = "Filters",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }


            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = adv,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Advanced") }
                        )
                        Text(
                            text = "Advanced",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }



            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = fgc,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Foreground CG") }
                        )
                        Text(
                            text = "FG Color",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }


            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = bgc,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "Background CG") }
                        )
                        Text(
                            text = "BG Color",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }


            item {
                Spacer(modifier = Modifier.width(12.dp)) // Add space between buttons
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5f))
                        .padding(4.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, // Align text to the bottom
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = crop,
                            contentDescription = "Your Icon Description",
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.navigate(route = "CropSelection") }
                        )
                        Text(
                            text = "Crop",
                            color = Color.Black,
                            fontSize = 10.sp,
//                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(5.dp) // Add padding at the bottom
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun NextButton(navController: NavController) {
    val merriFont = FontFamily(Font(R.font.merri, FontWeight.Normal))

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .width(120.dp)
                .padding(15.dp)
        ) {

            TextButton(
                onClick = {
                    navController.navigate(route = "EditingScreen")
                },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color.DarkGray)
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontFamily = merriFont,
                    fontWeight = FontWeight.Thin,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center
                )
            }
        }}
}


@Composable
fun ImageCard(
    painter: Painter,//for displaying the image
    contentDescription: String,
    modifier: Modifier=Modifier
)
{
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box(modifier = Modifier
            .height(800.dp)
            .fillMaxSize())
        {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )

            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(0.5f)
                        ),
                        startY = 300f
                    )
                ))
        }
    }
}


@Composable
fun StudioFont() {
    val merriFont = FontFamily(Font(R.font.merri, FontWeight.Normal))

    val gradientcolors = listOf(
        Color.Transparent,  Color.Transparent, Color.Black.copy(alpha = 0.3f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(brush = Brush.verticalGradient(gradientcolors))
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontSize = 60.sp,
                                fontFamily = merriFont
                            )
                        ) {
                            append("I")
                        }
                        append("nstant ")
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontSize = 60.sp,
                                fontFamily = merriFont
                            )
                        ) {
                            append("S")
                        }
                        append("tudio")
                    },
                    fontSize = 45.sp,
                    color = Color.White.copy(0.9f),
                    fontFamily =merriFont,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp) // Add padding to center vertically
                )
            }
        }
    }
}


@Composable
fun UploadImage(onImageUploaded: (Uri) -> Unit) {
    val merriFont = FontFamily(Font(R.font.merri, FontWeight.Normal))

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
                onImageUploaded(it)
            }
        }
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 29.dp)
            .fillMaxSize()
    ) {
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 55.dp)
                    .width(360.dp)
                    .fillMaxWidth()
                    .height(600.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.weight(0.8f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .height(200.dp)
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
                    .clickable { // Make the whole box clickable
                        galleryLauncher.launch("image/*")
                    }
            ) {
                Text(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontFamily = merriFont,
                    fontWeight = FontWeight.Thin,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center,
                    text = "+",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}