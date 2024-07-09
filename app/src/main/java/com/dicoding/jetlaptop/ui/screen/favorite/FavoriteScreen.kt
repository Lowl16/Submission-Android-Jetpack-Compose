package com.dicoding.jetlaptop.ui.screen.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetlaptop.R
import com.dicoding.jetlaptop.di.Injection
import com.dicoding.jetlaptop.model.Laptop
import com.dicoding.jetlaptop.ui.ViewModelFactory
import com.dicoding.jetlaptop.ui.common.UiState
import com.dicoding.jetlaptop.ui.component.EmptyData
import com.dicoding.jetlaptop.ui.screen.home.ListLaptop
import com.dicoding.jetlaptop.ui.theme.JetLaptopTheme

@Composable
fun FavoriteScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteLaptop()
            }
            is UiState.Success -> {
                FavoriteContent(
                    listLaptop = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateLaptop(id, newState)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    listLaptop: List<Laptop>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        if (listLaptop.isNotEmpty()) {
            ListLaptop(
                listLaptop = listLaptop,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            EmptyData(
                message = stringResource(R.string.empty_favorite)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteContentPreview() {
    val dummyLaptop = listOf(
        Laptop(
            id = 0,
            image = R.drawable.image_hp_0,
            price = 6449000,
            name = "HP 15s-fq5148TU",
            brand = "HP",
            description = "Description:\n- Processor: Intel Core i3-1215U\n- Memory: 4 GB\n- Storage: 512 GB SSD\nDo your thing, all day and wherever you prefer, thanks to the HP 15.6\" Laptop PC with a lightweight design and long battery life. Enjoy comfortable computing with a flicker-free screen and experience reliable performance thanks to the Intel® Core™ Processor. Plus, made with the planet in mind, is EPEAT® Silver registered and ENERGY STAR® certified.",
            rating = 4.9
        ),
        Laptop(
            id = 1,
            image = R.drawable.image_asus_1,
            price = 5885000,
            name = "ASUS Vivobook 14 A1400EA",
            brand = "ASUS",
            description = "Description:\n- Processor: Intel Core i3-1115G4\n- Memory: 8 GB\n- Storage: 256 GB SSD\nGood for work and play, the ASUS Vivobook 14 is an entry-level laptop with powerful performance and a captivating display. The NanoEdge display offers a wide viewing angle of up to 178° with a matte anti-glare coating to provide the best experience. Powered by Intel® processors with options up to Intel® Core™ i7 and memory up to 8GB DDR4 3200MHz, it delivers robust performance. The storage system design with up to 512GB PCIe® SSD provides fast data read and write speeds. Intel® Optane™ memory support is also available to further accelerate your experience.",
            rating = 4.8
        ),
        Laptop(
            id = 2,
            image = R.drawable.image_acer_2,
            price = 11648999,
            name = "Acer Nitro 5 AN515-58",
            brand = "Acer",
            description = "Description:\n- Processor: Intel Core i5-12500H\n- Memory: 16 GB\n- Storage: 512 GB SSD\nBetter FPS with a smoother display featuring Intel 1st generation processors. Up to a 27% improvement from the previous generation processor. And an increase of up to approximately 10% when using Nitro Sense. More stable performance and longer durability with +25% better airflow using Acer CoolBoost (quad exhaust fan). Dual NVMe slots, with a maximum 2TB SSD. More stylish with a 4-zone RGB keyboard. A smoother display that is 2 times smoother than a 60Hz refresh rate.",
            rating = 5.0
        ),
    )

    JetLaptopTheme {
        FavoriteContent(
            listLaptop = dummyLaptop,
            navigateToDetail = {},
            onFavoriteIconClicked = { _, _ -> }
        )
    }
}