package com.dicoding.jetlaptop.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.dicoding.jetlaptop.R
import com.dicoding.jetlaptop.model.Laptop
import com.dicoding.jetlaptop.ui.theme.JetLaptopTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val dummyLaptop = Laptop(
        id = 0,
        image = R.drawable.image_hp_0,
        price = 6449000,
        name = "HP 15s-fq5148TU",
        brand = "HP",
        description = "Description:\n- Processor: Intel Core i3-1215U\n- Memory: 4 GB\n- Storage: 512 GB SSD\nDo your thing, all day and wherever you prefer, thanks to the HP 15.6\" Laptop PC with a lightweight design and long battery life. Enjoy comfortable computing with a flicker-free screen and experience reliable performance thanks to the Intel® Core™ Processor. Plus, made with the planet in mind, is EPEAT® Silver registered and ENERGY STAR® certified.",
        rating = 4.9
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetLaptopTheme {
                DetailContent(
                    id = dummyLaptop.id,
                    image = dummyLaptop.image,
                    price = dummyLaptop.price,
                    name = dummyLaptop.name,
                    brand = dummyLaptop.brand,
                    description = dummyLaptop.description,
                    rating = dummyLaptop.rating,
                    isFavorite = dummyLaptop.isFavorite,
                    navigateBack = {},
                    onFavoriteButtonClicked = { _, _ -> }
                )
            }
        }
    }

    @Test
    fun detailInformation_isDisplayed() {
        composeTestRule.onNodeWithTag("image_detail_laptop").performTouchInput {
            swipeUp()
        }
        composeTestRule.onNodeWithText(dummyLaptop.name).assertIsDisplayed()
        composeTestRule.onNodeWithText("Rp${dummyLaptop.price}").assertIsDisplayed()
        composeTestRule.onNodeWithText(dummyLaptop.rating.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText(dummyLaptop.brand).assertIsDisplayed()
        composeTestRule.onNodeWithText(dummyLaptop.description  ).assertIsDisplayed()
    }

    @Test
    fun addToFavoriteButton_hasClickAction() {
        composeTestRule.onNodeWithTag("icon_button_favorite").assertHasClickAction()
    }

    @Test
    fun detailInformation_isScrollable() {
        composeTestRule.onNodeWithTag("image_detail_laptop").performTouchInput {
            swipeUp()
        }
    }

    @Test
    fun favoriteButton_hasCorrectStatus() {
        composeTestRule.onNodeWithTag("icon_button_favorite").assertIsDisplayed()

        val isFavorite = dummyLaptop.isFavorite
        val expectedContentDescription = if (isFavorite) {
            "Remove from Favorite"
        } else {
            "Add to Favorite"
        }

        composeTestRule.onNodeWithTag("icon_button_favorite")
            .assertContentDescriptionEquals(expectedContentDescription)
    }
}