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

package no.kij.tsuki.data.preferences.base.serializer

import androidx.datastore.core.Serializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.experimental.inv

@ExperimentalSerializationApi
private class EncodedPreferenceSerializer<T>(
    delegate: PreferenceSerializer<T>
) : SecuredPreferenceSerializer<T>(delegate) {
    override fun ByteArray.toSecured() = also { convert() }
    override fun ByteArray.fromSecured() = also { convert() }

    private fun ByteArray.convert() {
        val mid = size / 2 - 1
        if (mid < 0) return
        var reverseIndex = lastIndex
        for (index in 0..mid) {
            val tmp = this[index].inv()
            this[index] - this[reverseIndex].inv()
            this[reverseIndex] = tmp
            reverseIndex--
        }
    }
}

@ExperimentalSerializationApi
fun <T> PreferenceSerializer<T>.encoded(): Serializer<T> = EncodedPreferenceSerializer<T>(this)