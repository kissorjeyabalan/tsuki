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

package no.kij.tsuki.ui.main.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import no.kij.tsuki.domain.base.usecase.invoke
import no.kij.tsuki.domain.base.usecase.sync
import no.kij.tsuki.domain.auth.usecase.ClearAuthenticationUseCase
import no.kij.tsuki.domain.auth.usecase.GetAnilistTokenUseCase
import no.kij.tsuki.domain.auth.usecase.ObserveAuthenticationUseCase
import no.kij.tsuki.ui.base.viewmodel.BaseViewModel
import no.kij.tsuki.ui.login.navigation.LoginNavGraph
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val clearAuthenticationUseCase: ClearAuthenticationUseCase,
    private val getAnilistTokenUseCase: GetAnilistTokenUseCase,
    private val observeAuthenticationUseCase: ObserveAuthenticationUseCase
) : BaseViewModel<MainState, Nothing>() {
    override val container = container<MainState, Nothing>(
        MainState(initialNavGraph = initialNavGraph)
    ) {
        observeAuthentication()
    }

    private val initialNavGraph
        get() = if (getAnilistTokenUseCase.sync().nonEmpty()) {
            TODO()
        } else {
            LoginNavGraph
        }

    fun clearAuthentication() {
        intent {
            clearAuthenticationUseCase()
        }
    }

    private fun observeAuthentication() {
        observeAuthenticationUseCase()

        intent {
            observeAuthenticationUseCase.flow.collect { active ->
                active.fold(
                    ifLeft = {
                        reduce { state.copy(isAuthenticated = false) }
                    },
                    ifRight = {
                        reduce { state.copy(isAuthenticated = it) }
                    }
                )
            }
        }
    }
}