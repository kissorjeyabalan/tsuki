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

import android.util.Base64
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.security.GeneralSecurityException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException

@ExperimentalSerializationApi
internal sealed class SecuredPreferenceSerializer<T>(
    private val preferenceSerializer: PreferenceSerializer<T>
) : Serializer<T> by preferenceSerializer {

    override suspend fun writeTo(t: T, output: OutputStream) = output.use { stream ->
        perform("failed writing secured value") {
            val securedOutput = ByteArrayOutputStream().use { stream ->
                preferenceSerializer.writeTo(t, stream)
                stream.toByteArray()
            }.toSecured()

            stream.write(Base64.encode(securedOutput, Base64.NO_WRAP))
        }
    }

    override suspend fun readFrom(input: InputStream): T = input.use { stream ->
        perform("failed reading secured value") {
            val securedInput = Base64.decode(stream.readBytes(), Base64.NO_WRAP)

            val normalInput = with(securedInput) {
                fromSecured().takeIf { isNotEmpty() } ?: this
            }

            preferenceSerializer.readFrom(normalInput.inputStream())
        }
    }

    private inline fun <R> perform(message: String, block: () -> R): R = try {
        block()
    } catch (e: GeneralSecurityException) {
        throw CorruptionException(message, e)
    } catch (e: SerializationException) {
        throw CorruptionException(message, e)
    }

    abstract fun ByteArray.toSecured(): ByteArray
    abstract fun ByteArray.fromSecured(): ByteArray
}