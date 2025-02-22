package com.cowday.pawtography.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cowday.pawtography.R
import com.cowday.pawtography.ui.theme.PawtographyTheme
import com.cowday.pawtography.ui.theme.blue

@Composable
fun CenterTitleAppBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 4.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            title()
        }
        navigationIcon()
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    PawtographyTheme {
        CenterTitleAppBar(
            title = {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.button_generate_dogs),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                Row(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            onClick = {

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
}