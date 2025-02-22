package com.cowday.pawtography

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.imageLoader
import com.cowday.pawtography.data.DogRepository
import com.cowday.pawtography.network.RetrofitClient
import com.cowday.pawtography.ui.navigation.PawtographyNavGraph
import com.cowday.pawtography.ui.theme.PawtographyTheme

class MainActivity : ComponentActivity() {

    private val dogRepository: DogRepository by lazy {
        DogRepository(RetrofitClient.api, (application as PawtographyApplication).dogDatabase)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel(
                modelClass = MainViewModel::class,
                factory = MainViewModel.MainViewModelFactory(
                    dogRepository,
                    application.imageLoader
                )
            )
            val navController = rememberNavController()
            PawtographyTheme {
                PawtographyNavGraph(
                    modifier = Modifier,
                    navController = navController,
                    viewModel
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PawtographyTheme {
        Greeting("Android")
    }
}