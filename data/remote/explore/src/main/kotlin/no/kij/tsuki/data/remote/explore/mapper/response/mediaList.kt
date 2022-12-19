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

package no.kij.tsuki.data.remote.explore.mapper.response

import arrow.core.prependTo
import no.kij.tsuki.common.core.or
import no.kij.tsuki.data.remote.base.type.MediaType
import no.kij.tsuki.data.remote.explore.MediaPageQuery
import no.kij.tsuki.domain.base.model.list.MediaListEntry
import no.kij.tsuki.domain.base.model.entry.CommonMediaEntry
import no.kij.tsuki.domain.base.model.entry.MediaEntry

internal fun <T : MediaEntry> MediaPageQuery.Data.mediaList(type: MediaType): List<T> =
    page.media.asSequence().mapNotNull { media ->
        media?.toModel<T>(type)
    }.toList()

@Suppress("UNCHECKED_CAST")
private fun <T : MediaEntry> MediaPageQuery.Medium.toModel(type: MediaType) =
    let { media ->
        media.toMedia(type) as T
    }

private fun MediaPageQuery.Medium.toMedia(type: MediaType) = type.onMediaEntry(
    anime = ::animeEntry,
    manga = ::mangaEntry
)

private fun MediaPageQuery.Medium.animeEntry() = let { entry ->
    MediaEntry.Anime(
        entry = mediaEntry(),
        episodes = null,
        nextEpisode = null
    )
}

private fun MediaPageQuery.Medium.mangaEntry() = let { entry ->
    MediaEntry.Manga(
        entry = mediaEntry(),
        chapters = null,
        volume = null
    )
}

internal fun <R> MediaType.onMediaEntry(
    anime: () -> R,
    manga: () -> R
): R = when(this) {
    MediaType.ANIME -> anime()
    MediaType.MANGA -> manga()
    else -> error("unsupport media type")
}