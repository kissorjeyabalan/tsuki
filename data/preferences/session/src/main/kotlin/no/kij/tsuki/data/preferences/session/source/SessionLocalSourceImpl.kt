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

package no.kij.tsuki.data.preferences.session.source

import androidx.datastore.core.DataStore
import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.left
import arrow.core.right
import arrow.core.toOption
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import no.kij.tsuki.data.preferences.base.handle
import no.kij.tsuki.data.preferences.session.model.Session
import no.kij.tsuki.domain.base.failure.Failure
import no.kij.tsuki.domain.base.logger.Logger
import no.kij.tsuki.domain.session.failure.SessionFailure
import no.kij.tsuki.domain.session.model.AnilistToken
import javax.inject.Inject

internal class SessionLocalSourceImpl @Inject constructor(
    private val dataStore: DataStore<Session>, private val logger: Logger
) : SessionLocalSource {
    @Suppress("USELESS_CAST")
    override val sessionActive
        get() = dataStore.data.map { session ->
            (session.anilistToken == null && session.isSessionActive).not().right() as Either<Failure, Boolean>
        }.catch { error ->
            logger.e(error, "Error observing session - setting session as inactive")
            emit(SessionFailure.CheckingActiveSession.left())
        }

    override suspend fun saveSession(anilistToken: AnilistToken) = Either.catch(f = {
        dataStore.updateData { session ->
            session.copy(anilistToken = anilistToken.token, isSessionActive = true)
        }
        logger.d("Token saved: ${anilistToken.token}")
    }, fe = { error ->
        logger.e(error, "Failed to save token")
        error.handle(ioException = { SessionFailure.SavingSession }, other = { Failure.Unknown })
    })

    override suspend fun clearActiveSession() = Either.catch(f = {
        dataStore.updateData { session -> session.copy(isSessionActive = false) }
        logger.d("Session cleared")
    }, fe = { error ->
        logger.e(error, "Failed clearing session")
        error.handle(ioException = { SessionFailure.ClearingSession }, other = { Failure.Unknown })
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
        dataStore.updateData { session -> session.copy(anilistToken = null) }
        logger.d("Deleted Anilist token")
    }, fe = { error ->
        logger.e(error, "Failed deleting Anilist token")
        error.handle(ioException = { SessionFailure.DeletingToken }, other = { Failure.Unknown })
    })
}