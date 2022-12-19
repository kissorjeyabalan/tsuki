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

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import no.kij.tsuki.domain.base.logger.Logger
import no.kij.tsuki.ui.explore.navigation.ExploreNavigator
import no.kij.tsuki.ui.login.navigation.LoginNavigator
import no.kij.tsuki.ui.login.view.destinations.LoginDestination
import timber.log.Timber

internal class Navigator(
    private val navigator: DestinationsNavigator
) : LoginNavigator, ExploreNavigator {
    override fun goBack() {
        navigator.navigateUp()
    }

    override fun toHome() {
        navigator.navigate(NavGraphs.home, onlyIfResumed = true) {
            popUpTo(LoginDestination) {
                inclusive = true
            }
        }
    }

    // Explore navigator
    override fun openMedia(id: Int, from: ExploreNavigator.From) {
        Timber.d("Navigating to $id from $from")
    }
}