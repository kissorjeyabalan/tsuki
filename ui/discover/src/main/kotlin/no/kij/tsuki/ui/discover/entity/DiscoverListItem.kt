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

package no.kij.tsuki.ui.discover.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import no.kij.tsuki.ui.discover.R

@Immutable
internal sealed interface DiscoverListItem : Parcelable {
    val mediaId: Int
    val title: String
    val cover: String
    val banner: String
    val format: Format

    @Parcelize
    data class AnimeListItem(
        override val mediaId: Int,
        override val title: String,
        override val cover: String,
        override val banner: String,
        override val format: Format
    ) : DiscoverListItem

    @Immutable
    enum class Format(@StringRes val value: Int) {
        TV(R.string.discover_entry_format_tv),
        TVShort(R.string.discover_entry_format_tv_short),
        Movie(R.string.discover_entry_format_movie),
        Special(R.string.discover_entry_format_special),
        Ova(R.string.discover_entry_format_ova),
        Ona(R.string.discover_entry_format_ona),
        Music(R.string.discover_entry_format_music),
        Manga(R.string.discover_entry_format_manga),
        Novel(R.string.discover_entry_format_novel),
        OneShot(R.string.discover_entry_format_one_shot),
        Unknown(R.string.discover_entry_format_unknown)
    }
}