package com.dicoding.jetlaptop.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetlaptop.R
import com.dicoding.jetlaptop.di.Injection
import com.dicoding.jetlaptop.ui.ViewModelFactory
import com.dicoding.jetlaptop.ui.common.UiState
import com.dicoding.jetlaptop.ui.theme.JetLaptopTheme

@Composable
fun DetailScreen(
    laptopId: Int,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getLaptopById(laptopId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    id = data.id,
                    image = data.image,
                    price = data.price,
                    name = data.name,
                    brand = data.brand,
                    description = data.description,
                    rating = data.rating,
                    isFavorite = data.isFavorite,
                    navigateBack = navigateBack,
                    onFavoriteButtonClicked = { id, state ->
                        viewModel.updateLaptop(id, state)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    id: Int,
    @DrawableRes image: Int,
    price: Int,
    name: String,
    brand: String,
    description: String,
    rating: Double,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("image_detail_laptop")
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rp${price}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "icon_rating",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier
                            .size(22.dp)
                    )
                    Text(
                        text = rating.toString(),
                        modifier = Modifier
                            .padding(start = 2.dp, end = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Build,
                        contentDescription = "icon_brand",
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(16.dp)
                    )
                    Text(
                        text = brand,
                        modifier = Modifier
                            .padding(start = 6.dp, end = 8.dp)
                    )
                }
            }
            Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
            Text(
                text = description,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .testTag("icon_button_back")
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
            )
        }
        IconButton(
            onClick = {
                onFavoriteButtonClicked(id, isFavorite)
            },
            modifier = Modifier
                .padding(end = 16.dp, top = 16.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.White)
                .testTag("icon_button_favorite")
        ) {
            Icon(
                imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = if (!isFavorite) stringResource(R.string.add_favorite) else stringResource(
                    R.string.delete_favorite),
                tint = if (!isFavorite) Color.Black else Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    JetLaptopTheme {
        DetailContent(
            id = 0,
            image = R.drawable.image_hp_0,
            price = 6449000,
            name = "HP 15s-fq5148TU",
            brand = "HP",
            description = "Description:\n- Processor: Intel Core i3-1215U\n- Memory: 8GB\n- Display: 39.6 cm 15.6\" diagonal FHD\nDo your thing, all day and wherever you prefer, thanks to the HP 15.6\" Laptop PC with a lightweight design and long battery life. Enjoy comfortable computing with a flicker-free screen and experience reliable performance thanks to the Intel® Core™ Processor. Plus, made with the planet in mind, is EPEAT® Silver registered and ENERGY STAR® certified.",
            rating = 4.9,
            isFavorite = false,
            navigateBack = {},
            onFavoriteButtonClicked = { _, _ -> }
        )
    }
}