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

import no.kij.tsuki.core.model.MediaEntry
import no.kij.tsuki.ui.discover.entity.DiscoverListItem

internal fun List<MediaEntry.Anime>.toDiscoverItems(): List<DiscoverListItem.AnimeListItem> =
    map(MediaEntry.Anime::toMediaItem)


private fun MediaEntry.Anime.toMediaItem() = DiscoverListItem.AnimeListItem(
    mediaId = id,
    title = title,
    cover = coverImage,
    banner = bannerImage,
    format = format.toEntity()
)

