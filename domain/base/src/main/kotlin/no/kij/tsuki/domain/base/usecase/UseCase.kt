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

package no.kij.tsuki.domain.base.usecase

import arrow.core.Either
import arrow.core.Option
import kotlinx.coroutines.runBlocking
import no.kij.tsuki.domain.base.failure.Failure

sealed interface UseCase<in P, out R> : suspend (P) -> R {
    fun sync(params: P): R = runBlocking {
        invoke(params)
    }
}

interface EitherUseCase<in P, out R> : UseCase<P, Either<Failure, R>>
interface OptionUseCase<in P, out R> : UseCase<P, Option<R>>

suspend operator fun <R> UseCase<Unit, R>.invoke(): R = this(Unit)
fun <R> UseCase<Unit, R>.sync(): R = sync(Unit)