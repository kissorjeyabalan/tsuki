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

package no.kij.tsuki.data.preferences.auth.source

import androidx.datastore.core.DataStore
import arrow.core.Either
import arrow.core.None
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import no.kij.tsuki.data.preferences.base.handle
import no.kij.tsuki.data.preferences.auth.model.Authentication
import no.kij.tsuki.domain.base.failure.Failure
import no.kij.tsuki.domain.base.logger.Logger
import no.kij.tsuki.domain.auth.failure.AuthFailure
import no.kij.tsuki.domain.auth.model.AnilistToken
import javax.inject.Inject

internal class AuthLocalSourceImpl @Inject constructor(
    private val dataStore: DataStore<Authentication>, private val logger: Logger
) : AuthLocalSource {
    @Suppress("USELESS_CAST")
    override val isAuthenticated
        get() = dataStore.data.map { auth ->
            (auth.anilistToken == null && auth.isAuthenticated).not().right() as Either<Failure, Boolean>
        }.catch { error ->
            logger.e(error, "Error observing authentication - setting unauthenticated")
            emit(AuthFailure.CheckingAuthentication.left())
        }

    override suspend fun saveToken(anilistToken: AnilistToken) = Either.catch(f = {
        dataStore.updateData { auth ->
            auth.copy(anilistToken = anilistToken.token, isAuthenticated = true)
        }
        logger.d("Token saved: ${anilistToken.token}")
    }, fe = { error ->
        logger.e(error, "Failed to save token")
        error.handle(ioException = { AuthFailure.SavingToken }, other = { Failure.Unknown })
    })

    override suspend fun clearAuthentication() = Either.catch(f = {
        dataStore.updateData { auth -> auth.copy(isAuthenticated = false) }
        logger.d("Authentication cleared")
    }, fe = { error ->
        logger.e(error, "Failed clearing authentication")
        error.handle(ioException = { AuthFailure.ClearingAuthentication }, other = { Failure.Unknown })
    })

    override suspend fun getAnilistToken() = dataStore.data.map { token ->
        token.anilistToken?.let {
            AnilistToken(it)
        }.toOption()
    }.catch { error ->
        logger.e(error, "Failed reading token from preferences")
        emit(None)
    }.first()

    override suspend fun deleteAnilistToken() = Either.catch(f = {
        dataStore.updateData { auth -> auth.copy(anilistToken = null) }
        logger.d("Deleted Anilist token")
    }, fe = { error ->
        logger.e(error, "Failed deleting Anilist token")
        error.handle(ioException = { AuthFailure.DeletingToken }, other = { Failure.Unknown })
    })
}