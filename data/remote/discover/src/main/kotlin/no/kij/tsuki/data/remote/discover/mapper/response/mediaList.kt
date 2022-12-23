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

package no.kij.tsuki.data.remote.discover.mapper.response

import no.kij.tsuki.core.model.MediaEntry
import no.kij.tsuki.data.remote.base.type.MediaType
import no.kij.tsuki.data.remote.discover.MediaPageQuery

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
    anime = ::animeEntry
)

private fun MediaPageQuery.Medium.animeEntry() = let { entry ->
    MediaEntry.Anime(
        entry = mediaEntry(),
        episodes = null,
        nextEpisode = null
    )
}

internal fun <R> MediaType.onMediaEntry(
    anime: () -> R
): R = when(this) {
    MediaType.ANIME -> anime()
    else -> error("unsupport media type")
}