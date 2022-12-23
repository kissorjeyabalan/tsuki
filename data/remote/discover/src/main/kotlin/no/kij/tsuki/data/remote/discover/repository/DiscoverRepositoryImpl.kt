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

package no.kij.tsuki.data.remote.discover.repository

import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import no.kij.tsuki.core.model.MediaEntry
import no.kij.tsuki.data.remote.discover.source.anime.AnimeDiscoverRemoteSource
import no.kij.tsuki.domain.base.failure.Failure
import no.kij.tsuki.domain.discover.repository.DiscoverRepository
import javax.inject.Inject

internal class DiscoverRepositoryImpl @Inject constructor(
    private val discoverAnimeSource: AnimeDiscoverRemoteSource
) : DiscoverRepository {
    override val trendingAnimeCollection
        get() = discoverAnimeSource.trendingCollection

    override val popularSeasonAnimeCollection: Flow<Either<Failure, List<MediaEntry.Anime>>>
        get() = discoverAnimeSource.popularSeasonCollection

    override val upcomingAnimeCollection: Flow<Either<Failure, List<MediaEntry.Anime>>>
        get() = discoverAnimeSource.upcomingCollection

    override val popularAllTimeAnimeCollection: Flow<Either<Failure, List<MediaEntry.Anime>>>
        get() = discoverAnimeSource.popularAllTimeCollection


}