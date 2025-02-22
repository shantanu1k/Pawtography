package com.cowday.pawtography.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cowday.pawtography.R

val sfProFontFamily = FontFamily(
    Font(R.font.sf_pro_regular, FontWeight.Normal),
    Font(R.font.sf_pro_medium, FontWeight.Medium),
    Font(R.font.sf_pro_bold, FontWeight.Bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    ),
    titleLarge = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    titleMedium = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    titleSmall = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    bodyLarge = TextStyle(
        fontFamily = sfProFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    labelLarge = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Light
    ),
    labelMedium = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    ),
    labelSmall = TextStyle(
        fontFamily = sfProFontFamily,
        fontSize = 10.sp,
        fontWeight = FontWeight.Light
    )
)