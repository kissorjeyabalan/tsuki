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

package no.kij.tsuki.data.remote.explore

import no.kij.tsuki.data.remote.base.type.MediaSeason
import java.time.LocalDate
import java.time.Month

val seasons = with(Month.values()) {
    drop(2) + take(2)
}.chunked(3)

/**
 * [
 *
 * [[MARCH, APRIL, MAY]],
 *
 * [[JUNE, JULY, AUGUST]],
 *
 * [[SEPTEMBER, OCTOBER, NOVEMBER]],
 *
 * [[DECEMBER, JANUARY, FEBRUARY]]
 *
 * ]
 */
internal val Month.season get() = when(this) {
    in seasons[0] -> MediaSeason.SPRING
    in seasons[1] -> MediaSeason.SUMMER
    in seasons[2] -> MediaSeason.FALL
    in seasons[3] -> MediaSeason.WINTER
    else -> MediaSeason.UNKNOWN__
}

internal fun MediaSeason.nextSeason(now: LocalDate): Pair<MediaSeason, Int> {
    return when (this) {
        MediaSeason.WINTER -> MediaSeason.SPRING to if (now.month == Month.DECEMBER) {
            now.year + 1
        } else {
            now.year
        }
        MediaSeason.SPRING -> MediaSeason.SUMMER to now.year
        MediaSeason.SUMMER -> MediaSeason.FALL to now.year
        MediaSeason.FALL -> MediaSeason.WINTER to now.year + 1
        MediaSeason.UNKNOWN__ -> MediaSeason.UNKNOWN__ to 0
    }
}