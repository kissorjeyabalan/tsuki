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

package no.kij.tsuki.data.preferences.auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.google.crypto.tink.Aead
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import no.kij.tsuki.data.preferences.base.serializer.encrypted
import no.kij.tsuki.data.preferences.auth.model.Authentication
import no.kij.tsuki.data.preferences.auth.serializer.AuthSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AuthDataStoreModule {
    private const val DATASTORE_FILE = "tsuki.auth.pb"

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun provideAuthDataStore(
        @ApplicationContext context: Context, aead: Aead
    ): DataStore<Authentication> = DataStoreFactory.create(serializer = AuthSerializer.encrypted(aead),
        corruptionHandler = ReplaceFileCorruptionHandler { Authentication() },
        produceFile = { context.dataStoreFile(DATASTORE_FILE) })

}