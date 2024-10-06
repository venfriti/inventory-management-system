package com.venfriti.inventorymanagement.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.venfriti.inventorymanagement.R


val montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_medium, FontWeight.Medium)
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
)

val customTypography = Typography(
    titleLarge = Typography.titleLarge.copy(fontFamily = montserrat),
    titleMedium = Typography.titleMedium.copy(fontFamily = montserrat),
    titleSmall = Typography.titleSmall.copy(fontFamily = montserrat),
    bodyLarge = Typography.bodyLarge.copy(fontFamily = montserrat),
    bodyMedium = Typography.bodyMedium.copy(fontFamily = montserrat),
    bodySmall = Typography.bodySmall.copy(fontFamily = montserrat),
    labelLarge = Typography.labelLarge.copy(fontFamily = montserrat),
    labelMedium = Typography.labelMedium.copy(fontFamily = montserrat),
    labelSmall = Typography.labelSmall.copy(fontFamily = montserrat)
)