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

package no.kij.tsuki.data.remote.explore.source.anime

import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import no.kij.tsuki.core.model.MediaEntry
import no.kij.tsuki.data.remote.base.type.MediaSort
import no.kij.tsuki.data.remote.base.type.MediaType
import no.kij.tsuki.data.remote.explore.nextSeason
import no.kij.tsuki.data.remote.explore.season
import no.kij.tsuki.data.remote.explore.source.ExploreRemoteSource
import no.kij.tsuki.domain.base.failure.Failure
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AnimeExploreRemoteSourceImpl @Inject constructor(
    private val delegate: ExploreRemoteSource
) : AnimeExploreRemoteSource {
    override val trendingCollection: Flow<Either<Failure, List<MediaEntry.Anime>>>
        get() = delegate.getExploreList(
            type = MediaType.ANIME,
            sort = MediaSort.TRENDING_DESC,
            season = null,
            seasonYear = null
        )

    override val popularSeasonCollection: Flow<Either<Failure, List<MediaEntry.Anime>>>
        get() {
            val now = LocalDate.now()
            return delegate.getExploreList(
                type = MediaType.ANIME,
                sort = MediaSort.POPULARITY_DESC,
                season = now.month.season,
                seasonYear = now.year
            )
        }

    override val upcomingCollection: Flow<Either<Failure, List<MediaEntry.Anime>>>
        get() {
            val now = LocalDate.now()
            val nextSeason = now.month.season.nextSeason(now)
            return delegate.getExploreList(
                type = MediaType.ANIME,
                sort = MediaSort.POPULARITY_DESC,
                season = nextSeason.first,
                seasonYear = nextSeason.second
            )
        }

    override val popularAllTimeCollection: Flow<Either<Failure, List<MediaEntry.Anime>>>
        get() = delegate.getExploreList(
            type = MediaType.ANIME,
            sort = MediaSort.POPULARITY_DESC,
            season = null,
            seasonYear = null
        )
}