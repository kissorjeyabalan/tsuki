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

package no.kij.tsuki.ui.base.component.media

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import no.kij.tsuki.ui.base.design.spacing


@Composable
fun <T> MediaSmallRow(mediaList: List<T>, content: @Composable (T) -> Unit) {
    LazyRow(
        horizontalArrangement =  Arrangement.spacedBy(MaterialTheme.spacing.mediumSpace),
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.extraMediumSpace,
            end = MaterialTheme.spacing.extraMediumSpace
        )
    ) {
        items(mediaList) { media ->
            content(media)
        }
    }
}