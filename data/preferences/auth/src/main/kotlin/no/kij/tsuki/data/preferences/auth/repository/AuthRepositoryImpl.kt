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

package no.kij.tsuki.data.preferences.auth.repository

import arrow.core.Either
import arrow.core.Option
import kotlinx.coroutines.flow.Flow
import no.kij.tsuki.data.preferences.auth.source.AuthLocalSource
import no.kij.tsuki.domain.base.failure.Failure
import no.kij.tsuki.domain.auth.model.AnilistToken
import no.kij.tsuki.domain.auth.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val source: AuthLocalSource
) : AuthRepository {
    override val isAuthenticated: Flow<Either<Failure, Boolean>>
        get() = source.isAuthenticated

    override suspend fun saveToken(anilistToken: AnilistToken): Either<Failure, Unit> =
        source.saveToken(anilistToken)

    override suspend fun clearAuthentication(): Either<Failure, Unit> = source.clearAuthentication()

    override suspend fun deleteAnilistToken(): Either<Failure, Unit> = source.deleteAnilistToken()

    override suspend fun getAnilistToken(): Option<AnilistToken> = source.getAnilistToken()
}