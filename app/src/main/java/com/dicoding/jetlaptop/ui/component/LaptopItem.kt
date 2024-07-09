package com.dicoding.jetlaptop.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.jetlaptop.R
import com.dicoding.jetlaptop.ui.theme.JetLaptopTheme

@Composable
fun LaptopItem(
    id: Int,
    name: String,
    image: Int,
    price: Int,
    rating: Double,
    isFavorite: Boolean,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = "image_laptop",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = name
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp${price}",
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "icon_rating",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .size(20.dp)
                    )
                    Text(
                        text = rating.toString()
                    )
                }
            }
        }
        Icon(
            imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "icon_favorite",
            tint = if (!isFavorite) Color.Black else Color.Red,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
                .testTag("icon_favorite")
                .clickable { onFavoriteIconClicked(id, !isFavorite) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LaptopItemPreview() {
    JetLaptopTheme {
        LaptopItem(
            id = 0,
            name = "HP 15s-fq5148TU",
            image = R.drawable.image_hp_0,
            price = 6449000,
            rating = 4.9,
            isFavorite = false,
            onFavoriteIconClicked = { _, _ -> }
        )
    }
}