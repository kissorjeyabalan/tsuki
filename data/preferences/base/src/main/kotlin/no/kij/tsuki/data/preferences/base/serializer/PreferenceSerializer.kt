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
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

@ExperimentalSerializationApi
interface PreferenceSerializer<T> : Serializer<T> {
    val serializer: KSerializer<T>

    override suspend fun writeTo(t: T, output: OutputStream) = output.use { stream ->
        stream.write(ProtoBuf.encodeToByteArray(serializer, t))
    }

    override suspend fun readFrom(input: InputStream): T =
        input.use { stream -> ProtoBuf.decodeFromByteArray(serializer, stream.readBytes()) }
}