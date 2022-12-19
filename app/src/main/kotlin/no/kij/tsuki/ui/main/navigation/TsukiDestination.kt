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

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.scope.DestinationScope
import io.sentry.compose.withSentryObservableEffect
import no.kij.tsuki.ui.login.view.destinations.LoginDestination
import no.kij.tsuki.ui.main.component.AuthExpiredDialog
import no.kij.tsuki.ui.main.viewmodel.MainViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialNavigationApi::class,
    ExperimentalMaterial3Api::class
)
internal fun TsukiDestination(
    useNavRail: Boolean,
    vm: MainViewModel
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberAnimatedNavController().withSentryObservableEffect().also { nav ->
        nav.navigatorProvider += bottomSheetNavigator
    }

    val state by vm.collectAsState()

    AuthExpiredDialog(visible = !state.isAuthenticated) {
        vm.clearAuthentication()
        navController.logout()
    }

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(16.dp)
    ) {
        Scaffold(
            bottomBar = {
                if (!useNavRail) {
                    NavigationBar(
                        modifier = Modifier.fillMaxWidth(),
                        navController = navController,
                        type = NavigationBarType.Bottom
                    )
                } else {
                    Spacer(
                        modifier = Modifier
                            .windowInsetsBottomHeight(WindowInsets.navigationBars)
                            .fillMaxWidth()
                    )
                }
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                if (useNavRail) {
                    NavigationBar(
                        modifier = Modifier.fillMaxHeight(),
                        navController = navController,
                        type = NavigationBarType.Rail
                    )
                }

                DestinationsNavHost(
                    engine = rememberAnimatedNavHostEngine(
                        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING
                    ),
                    navController = navController,
                    navGraph = NavGraphs.root,
                    startRoute = state.initialNavGraph,
                    dependenciesContainerBuilder = {
                        dependency(currentNavigator())
                    }
                )
            }

        }
    }
}

@Composable
private fun DestinationScope<*>.currentNavigator(): Navigator = remember {
    Navigator(navigator = destinationsNavigator)
}

private fun NavController.logout() {
    navigate(LoginDestination) {
        popUpTo(NavGraphs.home) {
            inclusive = true
        }
    }
}