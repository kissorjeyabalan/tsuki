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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import no.kij.tsuki.ui.base.design.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreChip(genre: String?, color: Color, onClick: () -> Unit) {
    SuggestionChip(
        label = {
            Text(
                text = genre?.lowercase().orEmpty(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.smallSpace)
            )
        },
        onClick = onClick,
        shape = CircleShape,
        colors = SuggestionChipDefaults.suggestionChipColors(containerColor = color.copy(alpha = 0.25f)),
        border = SuggestionChipDefaults.suggestionChipBorder(borderColor = Color.Transparent)
    )
}