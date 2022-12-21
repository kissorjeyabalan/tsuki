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

package no.kij.tsuki.core.common

val Int.Companion.zero inline get() = 0
fun Int?.or(default: Int) = this ?: default
fun Int?.orZero() = or(Int.zero)

val Long.Companion.zero inline get() = 0L
fun Long?.or(default: Long) = this ?: default
fun Long?.orZero() = or(Long.zero)

val Double.Companion.zero inline get() = 0.0
fun Double?.or(default: Double) = this ?: default
fun Double?.orZero() = or(Double.zero)

val Float.Companion.zero inline get() = 0.0f
fun Float?.or(default: Float) = this ?: default
fun Float?.orZero() = or(Float.zero)