package com.dicoding.jetlaptop.ui.screen.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetlaptop.R
import com.dicoding.jetlaptop.di.Injection
import com.dicoding.jetlaptop.model.Laptop
import com.dicoding.jetlaptop.model.LaptopData
import com.dicoding.jetlaptop.ui.ViewModelFactory
import com.dicoding.jetlaptop.ui.common.UiState
import com.dicoding.jetlaptop.ui.component.EmptyData
import com.dicoding.jetlaptop.ui.component.LaptopItem
import com.dicoding.jetlaptop.ui.component.Search
import com.dicoding.jetlaptop.ui.theme.JetLaptopTheme

@Composable
fun HomeScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    val query by viewModel.query

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.searchLaptop(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::searchLaptop,
                    listLaptop = uiState.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateLaptop(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listLaptop: List<Laptop>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit
) {
    Column {
        Search(
            query = query,
            onQueryChange = onQueryChange
        )
        if (listLaptop.isNotEmpty()) {
            ListLaptop(
                listLaptop = listLaptop,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyData(
                message = stringResource(R.string.empty_data),
                modifier = Modifier
                    .testTag("empty_data")
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListLaptop(
    listLaptop: List<Laptop>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .testTag("lazy_column")
    ) {
        items(listLaptop, key = { it.id }) { item ->
            LaptopItem(
                id = item.id,
                name = item.name,
                image = item.image,
                price = item.price,
                rating = item.rating,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(item.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    JetLaptopTheme {
        HomeContent(
            query = "Search JetLaptop",
            onQueryChange = {},
            listLaptop = LaptopData.dummyLaptop,
            navigateToDetail = {},
            onFavoriteIconClicked = { _, _ -> }
        )
    }
}