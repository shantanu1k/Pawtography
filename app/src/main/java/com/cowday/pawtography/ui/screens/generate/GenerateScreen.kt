package com.cowday.pawtography.ui.screens.generate

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cowday.pawtography.MainViewModel
import com.cowday.pawtography.R
import com.cowday.pawtography.data.DogRepository
import com.cowday.pawtography.database.DogDatabase
import com.cowday.pawtography.network.RetrofitClient
import com.cowday.pawtography.ui.components.CenterTitleAppBar
import com.cowday.pawtography.ui.components.CustomButton
import com.cowday.pawtography.ui.navigation.PawtographyNavScreen
import com.cowday.pawtography.ui.theme.blue

@Composable
fun GenerateScreen(
    navController: NavController,
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
                        text = stringResource(R.string.button_generate_dogs),
                        style = MaterialTheme.typography.titleLarge,
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
        val imageUrl: MutableState<String?> = remember { mutableStateOf(null) }
        val generateScreenState = viewModel.generateDogsScreenState.collectAsState()
        val isLoading = remember { mutableStateOf(false) }

        when(val state = generateScreenState.value) {
            is MainViewModel.GenerateDogsScreenState.Error -> {
                isLoading.value = false
                Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
            }
            MainViewModel.GenerateDogsScreenState.Initial -> {
                isLoading.value = false
            }
            MainViewModel.GenerateDogsScreenState.Loading -> {
                isLoading.value = true
            }
            is MainViewModel.GenerateDogsScreenState.Success -> {
                isLoading.value = false
                imageUrl.value = state.imageUrl
            }
        }

        when(LocalConfiguration.current.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                MainContentPortrait(
                    imageUrl = imageUrl,
                    isLoading = isLoading.value,
                    onGenerateClick = {
                        viewModel.getDogImage()
                    },
                    modifier = Modifier.padding(contentPadding)
                )
            }
            else -> {
                MainContentLandscape(
                    imageUrl = imageUrl,
                    isLoading = isLoading.value,
                    onGenerateClick = {
                        viewModel.getDogImage()
                    },
                    modifier = Modifier.padding(contentPadding)
                )
            }
        }

    }
}

@Composable
private fun MainContentPortrait(
    imageUrl: MutableState<String?>,
    isLoading: Boolean,
    onGenerateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val targetHeight = LocalConfiguration.current.screenHeightDp * 0.4f
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = imageUrl.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(targetHeight.dp),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(36.dp))
        CustomButton(
            text = stringResource(R.string.button_generate),
            onClick = { onGenerateClick() },
            isEnabled = !isLoading
        )
    }
}

@Composable
private fun MainContentLandscape(
    imageUrl: MutableState<String?>,
    isLoading: Boolean,
    onGenerateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val targetWidth = LocalConfiguration.current.screenWidthDp * 0.48f
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomButton(
            text = stringResource(R.string.button_generate),
            onClick = { onGenerateClick() },
            isEnabled = !isLoading
        )
        AsyncImage(
            model = imageUrl.value,
            modifier = Modifier
                .fillMaxHeight()
                .width(targetWidth.dp),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun GenerateScreenPortraitPreview() {
    GenerateScreen(
        viewModel = MainViewModel(
            DogRepository(
                RetrofitClient.api,
                DogDatabase.getDatabase(
                    LocalContext.current
                )
            )
        ),
        navController = NavController(LocalContext.current)
    )
}

@Preview(widthDp = 640, heightDp = 360)
@Composable
private fun GenerateScreenLandscapePreview() {
    GenerateScreen(
        viewModel = MainViewModel(
            DogRepository(
                RetrofitClient.api,
                DogDatabase.getDatabase(
                    LocalContext.current
                )
            )
        ),
        navController = NavController(LocalContext.current)
    )
}