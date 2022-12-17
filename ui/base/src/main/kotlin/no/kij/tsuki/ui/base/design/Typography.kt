/*
 * Copyright (C) 2022 Kissor Jeyabalan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.kij.tsuki.ui.base.design

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal val Roboto = FontFamily.Default

internal val AppTypography = Typography(
    labelLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.10000000149011612.sp,
        lineHeight = 16.sp,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.10000000149011612.sp,
        lineHeight = 16.sp,
        fontSize = 11.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 24.sp,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.10000000149011612.sp,
        lineHeight = 16.sp,
        fontSize = 12.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 40.sp,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 36.sp,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 32.sp,
        fontSize = 24.sp
    ),
    displayLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 64.sp,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 52.sp,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 44.sp,
        fontSize = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.sp,
        lineHeight = 28.sp,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp,
        lineHeight = 24.sp,
        fontSize = 16.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
        fontSize = 14.sp
    ),
)