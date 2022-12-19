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

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val NoSpace = 0.dp
private val ExtraSmallSpace = 4.dp
private val SmallSpace = 8.dp
private val SmallMediumSpace = 12.dp
private val MediumSpace = 16.dp
private val ExtraMediumSpace = 24.dp
private val LargeSpace = 32.dp
private val ExtraLargeSpace = 40.dp
private val LargestSpace = 64.dp
private val BannerHeight = 128.dp

private val MediaCardWidth = 140.dp
private val MediaCardHeight = 200.dp

@Immutable
data class Spacing(
    val noSpace: Dp = NoSpace,
    val extraSmallSpace: Dp = ExtraSmallSpace,
    val smallSpace: Dp = SmallSpace,
    val smallMediumSpace: Dp = SmallMediumSpace,
    val mediumSpace: Dp = MediumSpace,
    val extraMediumSpace: Dp = ExtraMediumSpace,
    val largeSpace: Dp = LargeSpace,
    val extraLargeSpace: Dp = ExtraLargeSpace,
    val largestSpace: Dp = LargestSpace,
    val bannerHeight: Dp = BannerHeight,

    val mediaCardWidth: Dp = MediaCardWidth,
    val mediaCardHeight: Dp = MediaCardHeight
)

internal val LocalSpacing = staticCompositionLocalOf { Spacing() }