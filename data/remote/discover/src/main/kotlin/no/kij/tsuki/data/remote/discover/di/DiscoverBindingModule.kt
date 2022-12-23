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

package no.kij.tsuki.data.remote.discover.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.kij.tsuki.data.remote.discover.repository.DiscoverRepositoryImpl
import no.kij.tsuki.data.remote.discover.source.DiscoverRemoteSource
import no.kij.tsuki.data.remote.discover.source.DiscoverRemoteSourceImpl
import no.kij.tsuki.data.remote.discover.source.anime.AnimeDiscoverRemoteSource
import no.kij.tsuki.data.remote.discover.source.anime.AnimeDiscoverRemoteSourceImpl
import no.kij.tsuki.domain.discover.repository.DiscoverRepository

@Module
@InstallIn(SingletonComponent::class)
internal sealed interface DiscoverBindingModule  {
    @Binds
    fun bindDiscoverRepository(impl: DiscoverRepositoryImpl): DiscoverRepository

    @Binds
    fun bindDiscoverRemoteSource(impl: DiscoverRemoteSourceImpl): DiscoverRemoteSource

    @Binds
    fun bindDiscoverAnimeRemoteSource(impl: AnimeDiscoverRemoteSourceImpl): AnimeDiscoverRemoteSource
}