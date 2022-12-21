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

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import no.kij.tsuki.ui.explore.entity.ExploreListItem
import no.kij.tsuki.ui.explore.view.ExploreCategory

internal data class ExploreState<T : ExploreListItem>(
    val trending: ImmutableList<T> = persistentListOf(),
    val popularSeason: ImmutableList<T> = persistentListOf(),
    val upcoming: ImmutableList<T> = persistentListOf(),
    val allTimePopular: ImmutableList<T> = persistentListOf(),

    val loadingTrending: Boolean = false,
    val loadingPopular: Boolean = false,
    val loadingUpcoming: Boolean = false,
    val loadingAllTime: Boolean = false,

    val errorTrending: Boolean = false,
    val errorPopular: Boolean = false,
    val errorUpcoming: Boolean = false,
    val errorAllTime: Boolean = false,

    val selectedCategory: ExploreCategory = ExploreCategory.Popular
) {
    val isLoading
        get() = if (isError) {
            false
        } else {
            !loadingTrending && !loadingPopular && !loadingUpcoming && !loadingAllTime
        }

    val isError
        get() = !errorTrending && !errorPopular && !errorUpcoming && !errorAllTime
}