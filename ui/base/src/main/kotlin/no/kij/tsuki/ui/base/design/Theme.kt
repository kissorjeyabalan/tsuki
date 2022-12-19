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

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun Tsuki(content: @Composable () -> Unit) {
    TsukiTheme(content = content)
}

@Composable
private fun TsukiTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
   /** val colorScheme = if (supportsDynamicTheming()) {
    val context = LocalContext.current
        if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (useDarkTheme) DarkColors else LightColors
    }**/

    CompositionLocalProvider(
        LocalSpacing provides Spacing()
    ) {
        MaterialTheme(
            //colorScheme = if (useDarkTheme) DarkColors else LightColors,
            colorScheme = LightColors,
            typography = AppTypography,
            content = content
        )
    }
}

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S