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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import no.kij.tsuki.ui.login.navigation.LoginNavGraph

@Composable
internal fun NavigationBar(
    navController: NavController,
    type: NavigationBarType,
    modifier: Modifier = Modifier
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val currentNavGraph by remember(currentBackStackEntry) {
        derivedStateOf(referentialEqualityPolicy()) { currentBackStackEntry?.destination?.navGraph() }
    }

    val isVisible by remember(currentNavGraph) {
        derivedStateOf { currentNavGraph != null && currentNavGraph !is LoginNavGraph }
    }

    val destinations = if (currentNavGraph == NavGraphs.home) {
        HomeNavigationBarItem.values
    } else {
        persistentListOf()
    }

    val currentDestination = currentBackStackEntry?.destination

    val isItemSelected = { item: NavigationBarItem ->
        currentDestination?.route in item.direction.destinationsByRoute.keys
    }

    val onDestinationClick = { destination: NavigationBarItem ->
        navController.navigate(destination)
    }

    val (enterAnimation, exitAnimation) = when (type) {
        NavigationBarType.Bottom -> slideInVertically { it } to slideOutVertically { it }
        NavigationBarType.Rail -> slideInHorizontally { -it } to slideOutHorizontally { -it }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = enterAnimation,
        exit = exitAnimation
    ) {
        when (type) {
            NavigationBarType.Bottom -> BottomNavigationBar(
                destinations = destinations,
                isItemSelected = isItemSelected,
                onClick = onDestinationClick
            )

            NavigationBarType.Rail -> RailNavigationBar(
                destinations = destinations,
                isItemSelected = isItemSelected,
                onClick = onDestinationClick
            )
        }

    }
}

@Composable
private fun BottomNavigationBar(
    destinations: ImmutableList<NavigationBarItem>,
    isItemSelected: (NavigationBarItem) -> Boolean,
    onClick: (NavigationBarItem) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = isItemSelected(destination),
                onClick = { onClick(destination) },
                icon = { NavigationBarIcon(destination) },
                label = { NavigationBarLabel(destination) },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f).compositeOver
                        (MaterialTheme.colorScheme.inversePrimary.copy())

                )
            )
        }
    }
}

@Composable
private fun RailNavigationBar(
    destinations: ImmutableList<NavigationBarItem>,
    isItemSelected: (NavigationBarItem) -> Boolean,
    onClick: (NavigationBarItem) -> Unit
) {
    NavigationRail(
        modifier = Modifier.padding(
            WindowInsets.systemBars.only(WindowInsetsSides.Start + WindowInsetsSides.Vertical)
                .asPaddingValues()
        )
    ) {
        Spacer(Modifier.weight(1f))
        destinations.forEach { destination ->
            NavigationRailItem(
                selected = isItemSelected(destination),
                onClick = { onClick(destination) },
                icon = { NavigationBarIcon(destination) },
                label = { NavigationBarLabel(destination) },
                alwaysShowLabel = false,
                colors = NavigationRailItemDefaults.colors(selectedTextColor = LocalContentColor.current)
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun NavigationBarIcon(destination: NavigationBarItem) {
    Icon(
        imageVector = destination.icon,
        contentDescription = stringResource(id = destination.label)
    )
}

@Composable
private fun NavigationBarLabel(destination: NavigationBarItem) {
    Text(text = stringResource(destination.label))
}