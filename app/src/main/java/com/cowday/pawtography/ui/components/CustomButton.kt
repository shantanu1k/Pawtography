package com.cowday.pawtography.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cowday.pawtography.R
import com.cowday.pawtography.ui.theme.PawtographyTheme
import com.cowday.pawtography.ui.theme.black
import com.cowday.pawtography.ui.theme.blue

@Composable
fun CustomButton(
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        shape = RoundedCornerShape(percent = 50),
        border = BorderStroke(width = 1.dp, color = black),
        colors = ButtonColors(
            containerColor = blue,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = blue,
            disabledContentColor = Color.Gray
        ),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isEnabled) {
                Color.White
            } else {
                Color.Gray
            }
        )
    }
}

@Preview
@Composable
private fun CustomButtonPreview() {
    PawtographyTheme {
        CustomButton(
            text = stringResource(R.string.button_generate),
            isEnabled = true,
            onClick = {}
        )
    }
}