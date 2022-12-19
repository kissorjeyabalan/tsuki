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

package no.kij.tsuki.ui.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Explore
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.NavGraphSpec
import kotlinx.collections.immutable.persistentListOf
import no.kij.tsuki.R
import no.kij.tsuki.ui.explore.navigation.ExploreNavGraph

internal enum class HomeNavigationBarItem(
    override val direction: NavGraphSpec,
    override val icon: ImageVector,
    override val label: Int
) : NavigationBarItem {
    Explore(ExploreNavGraph, Icons.TwoTone.Explore, R.string.navbar_destination_explore);

    companion object {
        @JvmField
        val values = persistentListOf(Explore)
    }
}