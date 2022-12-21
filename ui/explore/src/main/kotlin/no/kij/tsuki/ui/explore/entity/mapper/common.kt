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

package no.kij.tsuki.ui.explore.entity.mapper

import no.kij.tsuki.core.model.CommonMediaEntry
import no.kij.tsuki.ui.explore.entity.ExploreListItem

internal fun CommonMediaEntry.Format.toEntity() = when (this) {
    CommonMediaEntry.Format.TV -> ExploreListItem.Format.TV
    CommonMediaEntry.Format.TV_SHORT -> ExploreListItem.Format.TVShort
    CommonMediaEntry.Format.MOVIE -> ExploreListItem.Format.Movie
    CommonMediaEntry.Format.SPECIAL -> ExploreListItem.Format.Special
    CommonMediaEntry.Format.OVA -> ExploreListItem.Format.Ova
    CommonMediaEntry.Format.ONA -> ExploreListItem.Format.Ona
    CommonMediaEntry.Format.MUSIC -> ExploreListItem.Format.Music
    CommonMediaEntry.Format.MANGA -> ExploreListItem.Format.Manga
    CommonMediaEntry.Format.NOVEL -> ExploreListItem.Format.Novel
    CommonMediaEntry.Format.ONE_SHOT -> ExploreListItem.Format.OneShot
    CommonMediaEntry.Format.UNKNOWN -> ExploreListItem.Format.Unknown
}