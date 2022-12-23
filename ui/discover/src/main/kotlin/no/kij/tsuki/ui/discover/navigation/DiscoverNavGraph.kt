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

package no.kij.tsuki.ui.discover.navigation

import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.spec.NavGraphSpec
import no.kij.tsuki.ui.discover.view.destinations.DiscoverScreenDestination

object DiscoverNavGraph : NavGraphSpec {
    override val route = "discover"
    override val startRoute = DiscoverScreenDestination routedIn this
    override val destinationsByRoute =
        listOf(DiscoverScreenDestination)
            .routedIn(this)
            .associateBy { it.route }
}