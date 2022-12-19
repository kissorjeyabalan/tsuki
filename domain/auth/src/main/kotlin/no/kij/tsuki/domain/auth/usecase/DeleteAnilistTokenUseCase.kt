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

package no.kij.tsuki.domain.auth.usecase

import arrow.core.Either
import no.kij.tsuki.domain.base.failure.Failure
import no.kij.tsuki.domain.base.usecase.EitherUseCase
import no.kij.tsuki.domain.auth.repository.AuthRepository
import javax.inject.Inject

class DeleteAnilistTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) : EitherUseCase<Unit, Unit> {
    override suspend fun invoke(p1: Unit): Either<Failure, Unit> = repository.deleteAnilistToken()
}