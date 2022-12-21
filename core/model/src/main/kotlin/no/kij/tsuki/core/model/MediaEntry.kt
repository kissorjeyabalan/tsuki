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

package no.kij.tsuki.core.model

import java.text.Format
import java.time.LocalDateTime

sealed class MediaEntry(
    val id: Int,
    val title: String,
    val coverImage: String,
    val bannerImage: String,
    val format: CommonMediaEntry.Format
) {
    constructor(entry: CommonMediaEntry) : this(
        id = entry.id,
        title = entry.title,
        coverImage = entry.coverImage,
        bannerImage = entry.bannerImage,
        format = entry.format
    )

    data class Anime(
        val entry: CommonMediaEntry,
        val episodes: Int?,
        val nextEpisode: NextEpisode?
    ) : MediaEntry(entry) {
        data class NextEpisode(
            val number: Int,
            val at: LocalDateTime
        )
    }
}