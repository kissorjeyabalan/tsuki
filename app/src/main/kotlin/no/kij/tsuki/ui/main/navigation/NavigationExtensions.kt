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

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.NavGraphSpec

internal fun NavController.navigate(destination: NavigationBarItem) {
    navigate(destination.direction) {
        launchSingleTop = true
        restoreState = true

        findStartDestination(graph)?.id?.let { id ->
            popUpTo(id) {
                saveState = true
            }
        }
    }
}

internal fun NavDestination.navGraph(): NavGraphSpec {
    hierarchy.forEach { destination ->
        NavGraphs.root.nestedNavGraphs.forEach { navGraph ->
            if (destination.route == navGraph.route) {
                return navGraph
            }
        }
    }

    error("Unknown nav graph for destination $route")
}

private tailrec fun findStartDestination(graph: NavDestination?): NavDestination? =
    if (graph is NavGraph) {
        findStartDestination(graph.startDestination)
    } else {
        graph
    }

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)