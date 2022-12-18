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

package no.kij.tsuki.data.preferences.session.repository

import arrow.core.Either
import arrow.core.Option
import kotlinx.coroutines.flow.Flow
import no.kij.tsuki.data.preferences.session.source.SessionLocalSource
import no.kij.tsuki.domain.base.failure.Failure
import no.kij.tsuki.domain.session.model.AnilistToken
import no.kij.tsuki.domain.session.repository.SessionRepository
import javax.inject.Inject

internal class SessionRepositoryImpl @Inject constructor(
    private val source: SessionLocalSource
) : SessionRepository {
    override val sessionActive: Flow<Either<Failure, Boolean>>
        get() = source.sessionActive

    override suspend fun saveSession(anilistToken: AnilistToken): Either<Failure, Unit> =
        source.saveSession(anilistToken)

    override suspend fun clearActiveSession(): Either<Failure, Unit> = source.clearActiveSession()

    override suspend fun deleteAnilistToken(): Either<Failure, Unit> = source.deleteAnilistToken()

    override suspend fun getAnilistToken(): Option<AnilistToken> = source.getAnilistToken()
}