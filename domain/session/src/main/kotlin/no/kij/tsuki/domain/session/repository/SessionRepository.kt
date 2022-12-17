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

package no.kij.tsuki.domain.session.repository

import arrow.core.Either
import arrow.core.Option
import kotlinx.coroutines.flow.Flow
import no.kij.tsuki.domain.base.failure.Failure
import no.kij.tsuki.domain.session.model.AnilistToken

interface SessionRepository {
    val sessionActive: Flow<Either<Failure, Boolean>>

    suspend fun saveSession(anilistToken: AnilistToken): Either<Failure, Unit>
    suspend fun getAnilistToken(): Option<AnilistToken>
    suspend fun clearActiveSession(): Either<Failure, Unit>
    suspend fun deleteAnilistToken(): Either<Failure, Unit>
}