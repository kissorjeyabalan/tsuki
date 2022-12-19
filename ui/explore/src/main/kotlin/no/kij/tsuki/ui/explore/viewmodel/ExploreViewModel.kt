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

package no.kij.tsuki.ui.explore.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import no.kij.tsuki.domain.base.model.entry.MediaEntry
import no.kij.tsuki.domain.base.usecase.invoke
import no.kij.tsuki.domain.explore.usecase.ObserveAllTimePopularAnimeUseCase
import no.kij.tsuki.domain.explore.usecase.ObserveSeasonPopularAnimeUseCase
import no.kij.tsuki.domain.explore.usecase.ObserveTrendingAnimeUseCase
import no.kij.tsuki.domain.explore.usecase.ObserveUpcomingAnimeUseCase
import no.kij.tsuki.ui.base.viewmodel.BaseViewModel
import no.kij.tsuki.ui.explore.entity.ExploreListItem
import no.kij.tsuki.ui.explore.entity.mapper.toExploreItems
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
internal class ExploreViewModel @Inject constructor(
    private val observeTrendingAnimeUseCase: ObserveTrendingAnimeUseCase,
    private val observeSeasonPopularAnimeUseCase: ObserveSeasonPopularAnimeUseCase,
    private val observeUpcomingAnimeUseCase: ObserveUpcomingAnimeUseCase,
    private val observeAllTimePopularAnimeUseCase: ObserveAllTimePopularAnimeUseCase
) : BaseViewModel<ExploreState<ExploreListItem.AnimeListItem>, Nothing>() {
    override val container =
        container<ExploreState<ExploreListItem.AnimeListItem>, Nothing>(ExploreState()) {
            observeLists()
        }

    private val trendingFlow
        get() = observeTrendingAnimeUseCase.flow

    private val popularSeasonFlow
        get() = observeSeasonPopularAnimeUseCase.flow

    private val upcomingFlow
        get() = observeUpcomingAnimeUseCase.flow

    private val popularAllTimeFlow
        get() = observeAllTimePopularAnimeUseCase.flow


    private fun observeLists() {
        refreshList()
        collectTrendingFlow()
        collectPopularSeasonFlow()
        collectUpcomingFlow()
        collectPopularAllTimeFlow()
    }

    private fun collectTrendingFlow() {
        intent {
            trendingFlow.collect { trending ->
                trending.fold(
                    ifLeft = { updateState { copy(errorTrending = true) } },
                    ifRight = { media ->
                        val items = media.entryMap()
                        reduce {
                            state.copy(
                                trending = items.toImmutableList(),
                                loadingTrending = false,
                                errorTrending = false
                            )
                        }
                    }
                )
            }
        }
    }

    private fun collectPopularSeasonFlow() {
        intent {
            popularSeasonFlow.collect { popular ->
                popular.fold(
                    ifLeft = { updateState { copy(errorPopular = true) } },
                    ifRight = { media ->
                        val items = media.entryMap()
                        reduce {
                            state.copy(
                                popularSeason = items.toImmutableList(),
                                loadingPopular = false,
                                errorPopular = false
                            )
                        }
                    }
                )
            }
        }
    }

    private fun collectUpcomingFlow() {
        intent {
            upcomingFlow.collect { upcoming ->
                upcoming.fold(
                    ifLeft = { updateState { copy(errorUpcoming = true) } },
                    ifRight = { media ->
                        val items = media.entryMap()
                        reduce {
                            state.copy(
                                upcoming = items.toImmutableList(),
                                loadingUpcoming = false,
                                errorUpcoming = false
                            )
                        }
                    }
                )
            }
        }
    }

    private fun collectPopularAllTimeFlow() {
        intent {
            popularAllTimeFlow.collect { allTime ->
                allTime.fold(
                    ifLeft = { updateState { copy(errorAllTime = true) } },
                    ifRight = { media ->
                        val items = media.entryMap()
                        reduce {
                            state.copy(
                                allTimePopular = items.toImmutableList(),
                                loadingAllTime = false,
                                errorAllTime = false
                            )
                        }
                    }
                )
            }
        }
    }

    internal fun refreshList() {
        updateState {
            copy(
                loadingTrending = true,
                loadingPopular = true,
                loadingUpcoming = true,
                loadingAllTime = true
            )
        }
        observeTrendingAnimeUseCase()
        observeSeasonPopularAnimeUseCase()
        observeUpcomingAnimeUseCase()
        observeAllTimePopularAnimeUseCase()
    }

    fun List<MediaEntry.Anime>.entryMap() = toExploreItems()
}