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

package no.kij.tsuki.data.remote.user.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.kij.tsuki.data.remote.user.UserRemoteSource
import no.kij.tsuki.data.remote.user.UserRemoteSourceImpl
import no.kij.tsuki.data.remote.user.manager.UserIdManagerImpl
import no.kij.tsuki.data.remote.user.repository.UserRepositoryImpl
import no.kij.tsuki.domain.user.manager.UserIdManager
import no.kij.tsuki.domain.user.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
internal sealed interface UserBindingModule {
    @Binds
    fun bindUserIdManager(impl: UserIdManagerImpl): UserIdManager

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindUserRemoteSource(impl: UserRemoteSourceImpl): UserRemoteSource
}