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

package no.kij.tsuki.data.remote.discover.source

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.fetchPolicyInterceptor
import com.apollographql.apollo3.cache.normalized.watch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import no.kij.tsuki.core.model.MediaEntry
import no.kij.tsuki.data.remote.base.failure.CommonRemoteFailure
import no.kij.tsuki.data.remote.base.interceptor.ReloadInterceptor
import no.kij.tsuki.data.remote.base.type.MediaSeason
import no.kij.tsuki.data.remote.base.type.MediaSort
import no.kij.tsuki.data.remote.base.type.MediaType
import no.kij.tsuki.data.remote.discover.MediaPageQuery
import no.kij.tsuki.data.remote.discover.mapper.response.mediaList
import no.kij.tsuki.domain.base.failure.Failure
import no.kij.tsuki.domain.base.logger.Logger
import no.kij.tsuki.domain.discover.failure.DiscoverFailure
import javax.inject.Inject

internal class DiscoverRemoteSourceImpl @Inject constructor(
    private val client: ApolloClient, private val reloadInterceptor: ReloadInterceptor, private val logger: Logger
) : DiscoverRemoteSource {
    override fun <T : MediaEntry> getDiscoverList(type: MediaType, sort: MediaSort, season: MediaSeason?, seasonYear:
    Int?):
            Flow<Either<Failure,
            List<T>>> =
        flow {
            val response = client.query(
                MediaPageQuery(
                    type = Optional.presentIfNotNull(type),
                    page = Optional.presentIfNotNull(1),
                    perPage = Optional.presentIfNotNull(10),
                    sort = Optional.presentIfNotNull(listOf(sort)),
                    season = Optional.presentIfNotNull(season),
                    seasonYear = Optional.presentIfNotNull(seasonYear)
                )
            )
                .fetchPolicyInterceptor(reloadInterceptor)
                .watch()
                .distinctUntilChanged()
                .map { res -> res.data?.mediaList<T>(type).orEmpty().right() }
                .distinctUntilChanged()
                .catch { error ->
                    logger.e(error, "Error collecting trending media")

                    emit(
                        when (CommonRemoteFailure(error)) {
                            CommonRemoteFailure.Cache -> Failure.Unknown
                            CommonRemoteFailure.Network -> DiscoverFailure.GetDiscover
                            CommonRemoteFailure.Response -> DiscoverFailure.GetDiscover
                            CommonRemoteFailure.Unknown -> Failure.Unknown
                        }.left()
                    )
                }
            emitAll(response)
        }
}