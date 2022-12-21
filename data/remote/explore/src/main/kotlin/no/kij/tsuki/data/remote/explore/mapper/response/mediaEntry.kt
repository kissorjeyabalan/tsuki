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

import no.kij.tsuki.core.common.or
import no.kij.tsuki.core.common.orZero
import no.kij.tsuki.data.remote.base.type.MediaFormat
import no.kij.tsuki.data.remote.explore.MediaPageQuery
import no.kij.tsuki.domain.base.model.entry.CommonMediaEntry

internal fun MediaPageQuery.Medium?.mediaEntry() = let { entry ->
    CommonMediaEntry(
        id = entry?.id.orZero(),
        title = entry?.title?.userPreferred.or(entry?.title?.romaji).orEmpty(),
        coverImage = entry?.coverImage?.extraLarge.orEmpty(),
        format = entry?.format.toFormat()
    )
}

private fun MediaFormat?.toFormat() = when(this) {
    MediaFormat.TV -> CommonMediaEntry.Format.TV
    MediaFormat.TV_SHORT -> CommonMediaEntry.Format.TV_SHORT
    MediaFormat.MOVIE -> CommonMediaEntry.Format.MOVIE
    MediaFormat.SPECIAL -> CommonMediaEntry.Format.SPECIAL
    MediaFormat.OVA -> CommonMediaEntry.Format.OVA
    MediaFormat.ONA -> CommonMediaEntry.Format.ONA
    MediaFormat.MUSIC -> CommonMediaEntry.Format.MUSIC
    MediaFormat.MANGA -> CommonMediaEntry.Format.MANGA
    MediaFormat.NOVEL -> CommonMediaEntry.Format.NOVEL
    MediaFormat.ONE_SHOT -> CommonMediaEntry.Format.ONE_SHOT
    MediaFormat.UNKNOWN__ -> CommonMediaEntry.Format.UNKNOWN
    null -> CommonMediaEntry.Format.UNKNOWN
}