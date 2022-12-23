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

package no.kij.tsuki.ui.discover.entity.mapper

import no.kij.tsuki.core.model.CommonMediaEntry
import no.kij.tsuki.ui.discover.entity.DiscoverListItem

internal fun CommonMediaEntry.Format.toEntity() = when (this) {
    CommonMediaEntry.Format.TV -> DiscoverListItem.Format.TV
    CommonMediaEntry.Format.TV_SHORT -> DiscoverListItem.Format.TVShort
    CommonMediaEntry.Format.MOVIE -> DiscoverListItem.Format.Movie
    CommonMediaEntry.Format.SPECIAL -> DiscoverListItem.Format.Special
    CommonMediaEntry.Format.OVA -> DiscoverListItem.Format.Ova
    CommonMediaEntry.Format.ONA -> DiscoverListItem.Format.Ona
    CommonMediaEntry.Format.MUSIC -> DiscoverListItem.Format.Music
    CommonMediaEntry.Format.MANGA -> DiscoverListItem.Format.Manga
    CommonMediaEntry.Format.NOVEL -> DiscoverListItem.Format.Novel
    CommonMediaEntry.Format.ONE_SHOT -> DiscoverListItem.Format.OneShot
    CommonMediaEntry.Format.UNKNOWN -> DiscoverListItem.Format.Unknown
}