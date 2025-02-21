package com.cowday.pawtography.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cowday.pawtography.R
import com.cowday.pawtography.ui.components.CustomButton
import com.cowday.pawtography.ui.navigation.PawtographyNavScreen
import com.cowday.pawtography.ui.theme.PawtographyTheme

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
    ) {
        Box(
            modifier = Modifier.weight(1f).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(R.string.title_home_page),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.secondary)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(
                text = stringResource(R.string.button_generate_dogs),
                onClick = {
                    navController.navigate(PawtographyNavScreen.GENERATE.route)
                }
            )
            CustomButton(
                text = stringResource(R.string.button_my_recently_generated_dogs),
                onClick = {
                    navController.navigate(PawtographyNavScreen.CACHE.route)
                }
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    PawtographyTheme {
        HomeScreen(navController = NavController(LocalContext.current))
    }
}