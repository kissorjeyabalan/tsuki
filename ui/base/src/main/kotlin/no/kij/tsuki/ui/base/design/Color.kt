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

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Seed: d87683 / 0xFFD87683
// Surface Tint Color: Color(0xFF9B404F)
// Shadow: Color(0xFF000000)
internal val LightColors = lightColorScheme(
    primary = Color(0xFF9B404F),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFD9DC),
    onPrimaryContainer = Color(0xFF400011),
    secondary = Color(0xFF765659),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFD9DC),
    onSecondaryContainer = Color(0xFF2C1518),
    tertiary = Color(0xFF785830),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDDB7),
    onTertiaryContainer = Color(0xFF2A1700),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color(0xFFFFFFFF),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFFFBFF),
    onBackground = Color(0xFF201A1A),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF201A1A),
    surfaceVariant = Color(0xFFF4DDDE),
    onSurfaceVariant = Color(0xFF524344),
    outline = Color(0xFF847374),
    inverseOnSurface = Color(0xFFFBEEEE),
    inverseSurface = Color(0xFF362F2F),
    inversePrimary = Color(0xFFFFB2BA),
    surfaceTint = Color(0xFF9B404F),
)

// Surface Tint Color: Color(0xFFFFB2BA)
// Shadow: Color(0xFF000000)
internal val DarkColors = darkColorScheme(
    primary = Color(0xFFFFB2BA),
    onPrimary = Color(0xFF5F1223),
    primaryContainer = Color(0xFF7D2938),
    onPrimaryContainer = Color(0xFFFFD9DC),
    secondary = Color(0xFFE5BDC0),
    onSecondary = Color(0xFF43292C),
    secondaryContainer = Color(0xFF5C3F42),
    onSecondaryContainer = Color(0xFFFFD9DC),
    tertiary = Color(0xFFE9BF8F),
    onTertiary = Color(0xFF442B07),
    tertiaryContainer = Color(0xFF5D411B),
    onTertiaryContainer = Color(0xFFFFDDB7),
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
    onError = Color(0xFF690005),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF201A1A),
    onBackground = Color(0xFFECE0E0),
    surface = Color(0xFF201A1A),
    onSurface = Color(0xFFECE0E0),
    surfaceVariant = Color(0xFF524344),
    onSurfaceVariant = Color(0xFFD7C1C3),
    outline = Color(0xFF9F8C8D),
    inverseOnSurface = Color(0xFF201A1A),
    inverseSurface = Color(0xFFECE0E0),
    inversePrimary = Color(0xFF9B404F),
    surfaceTint = Color(0xFFFFB2BA),
)