package com.cowday.pawtography.ui.screens.cache

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.cowday.pawtography.MainViewModel
import com.cowday.pawtography.R
import com.cowday.pawtography.data.DogRepository
import com.cowday.pawtography.database.DogDatabase
import com.cowday.pawtography.network.RetrofitClient
import com.cowday.pawtography.ui.components.CenterTitleAppBar
import com.cowday.pawtography.ui.components.CustomButton
import com.cowday.pawtography.ui.navigation.PawtographyNavScreen
import com.cowday.pawtography.ui.theme.PawtographyTheme
import com.cowday.pawtography.ui.theme.blue

@Composable
fun CacheScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterTitleAppBar(
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .shadow(elevation = 2.dp),
                title = {
                    Text(
                        text = stringResource(R.string.button_my_recently_generated_dogs),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                },
                navigationIcon = {
                    Row(
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                onClick = {
                                    navController.popBackStack(
                                        route = PawtographyNavScreen.HOME.route,
                                        inclusive = false
                                    )
                                },
                                interactionSource = null
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.round_arrow_back_ios_24),
                            tint = blue,
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(R.string.label_back),
                            color = blue,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        when(LocalConfiguration.current.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                MainContentPortrait(contentPadding, viewModel)
            }
            else -> {
                MainContentLandscape(contentPadding, viewModel)
            }
        }
    }
}

@Composable
private fun MainContentPortrait(
    contentPadding: PaddingValues,
    viewModel: MainViewModel
) {
    val targetHeight = LocalConfiguration.current.screenHeightDp * 0.4f
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(color = MaterialTheme.colorScheme.secondary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val listState = rememberLazyListState()
        val dogImages = viewModel.getRecentDogRecords().collectAsState(null)
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .height(targetHeight.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dogImages.value ?: emptyList()) {
                AsyncImage(
                    model = it.imageUrl,
                    modifier = Modifier
                        .fillParentMaxHeight()
                        .fillParentMaxWidth(0.93f),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomButton(
            modifier = Modifier,
            text = stringResource(R.string.button_clear_dogs),
            onClick = {
                viewModel.deleteAllDogRecords()
            },
        )
    }
}

@Composable
private fun MainContentLandscape(
    contentPadding: PaddingValues,
    viewModel: MainViewModel
) {
    val targetWidth = LocalConfiguration.current.screenWidthDp * 0.45f
    Row (
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(color = MaterialTheme.colorScheme.secondary),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val listState = rememberLazyListState()
        val dogImages = viewModel.getRecentDogRecords().collectAsState(null)
        CustomButton(
            modifier = Modifier,
            text = stringResource(R.string.button_clear_dogs),
            onClick = {
                viewModel.deleteAllDogRecords()
            },
        )
        Spacer(modifier = Modifier.width(24.dp))
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxHeight()
                .width(targetWidth.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dogImages.value ?: emptyList()) {
                AsyncImage(
                    model = it.imageUrl,
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .fillParentMaxHeight(0.93f),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun CacheScreenPortraitPreview() {
    PawtographyTheme {
        CacheScreen(
            navController = rememberNavController(),
            viewModel = MainViewModel(
                DogRepository(
                    RetrofitClient.api,
                    DogDatabase.getDatabase(
                        LocalContext.current
                    )
                )
            )
        )
    }
}

@Preview(heightDp = 360, widthDp = 640)
@Composable
private fun CacheScreenLandscapePreview() {
    PawtographyTheme {
        CacheScreen(
            navController = rememberNavController(),
            viewModel = MainViewModel(
                DogRepository(
                    RetrofitClient.api,
                    DogDatabase.getDatabase(
                        LocalContext.current
                    )
                )
            )
        )
    }
}