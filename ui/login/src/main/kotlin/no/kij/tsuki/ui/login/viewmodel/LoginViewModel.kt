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

package no.kij.tsuki.ui.login.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import no.kij.tsuki.domain.base.usecase.invoke
import no.kij.tsuki.domain.auth.model.AnilistToken
import no.kij.tsuki.domain.auth.usecase.SaveTokenUseCase
import no.kij.tsuki.domain.user.usecase.SaveUserIdUseCase
import no.kij.tsuki.ui.base.viewmodel.BaseViewModel
import no.kij.tsuki.ui.login.LOGIN_DEEP_LINK_TOKEN
import no.kij.tsuki.ui.login.R
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase
) : BaseViewModel<LoginState, Nothing>() {
    override val container = container<LoginState, Nothing>(LoginState()) {
        saveAnilistToken(savedStateHandle.get<String>(LOGIN_DEEP_LINK_TOKEN))
    }

    private fun saveAnilistToken(token: String?) {
        if (token.isNullOrBlank()) return

        intent {
            reduce { state.copy(loading = true) }

            val parsedToken = token.substringBefore(TOKEN_SEPARATOR)
            saveToken(parsedToken)
        }
    }

    private suspend fun saveToken(token: String) {
        saveTokenUseCase(AnilistToken(token)).fold(
            ifLeft = {
                updateState { copy(loading = false, error = R.string.save_token_error) }
            },
            ifRight = { saveUserId()}
        )
    }

    private suspend fun saveUserId() {
        saveUserIdUseCase().fold(
            ifLeft = {
                updateState { copy(loading = false, error = R.string.fetch_userid_error) }
            },
            ifRight = {
                updateState { copy(saved = true, loading = false, error = null) }
            }
        )
    }

    companion object {
        private const val TOKEN_SEPARATOR = '&'
    }
}