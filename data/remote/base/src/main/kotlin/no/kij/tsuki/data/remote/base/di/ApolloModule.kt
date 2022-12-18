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

package no.kij.tsuki.data.remote.base.di

import android.content.Context
import arrow.core.None.orNull
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.api.CacheKey
import com.apollographql.apollo3.cache.normalized.api.CacheKeyGenerator
import com.apollographql.apollo3.cache.normalized.api.CacheKeyGeneratorContext
import com.apollographql.apollo3.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.sentry.apollo3.sentryTracing
import no.kij.tsuki.domain.base.usecase.invoke
import no.kij.tsuki.domain.session.usecase.DeleteAnilistTokenUseCase
import no.kij.tsuki.domain.session.usecase.GetAnilistTokenUseCase
import okhttp3.OkHttpClient
import java.net.HttpURLConnection
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApolloModule {
    private const val ANILIST_BASE_URL = "https://graphql.anilist.co"
    private const val CACHE_DATABASE = "tsuki_ani.db"
    private const val CACHE_ID_KEY = "id"
    private const val CACHE_TYPE_KEY = "__typename"

    @Provides
    @Singleton
    fun provideApolloCache(
        @ApplicationContext context: Context
    ): NormalizedCacheFactory = SqlNormalizedCacheFactory(context, CACHE_DATABASE)

    @Provides
    @Singleton
    @AnilistTokenInterceptor
    fun provideAnilistTokenInterceptor(
        getAnilistToken: GetAnilistTokenUseCase
    ): HttpInterceptor = object : HttpInterceptor {
        override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse =
            request.newBuilder().addHeader("Authorization", "Bearer ${getAnilistToken().orNull()?.token}")
                .addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").build()
                .let { chain.proceed(it) }
    }

    @Provides
    @Singleton
    @SessionInterceptor
    fun provideSessionInterceptor(
        deleteSession: DeleteAnilistTokenUseCase
    ): HttpInterceptor = object : HttpInterceptor {
        override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {
            return chain.proceed(request).also { response ->
                if (response.statusCode == HttpURLConnection.HTTP_BAD_REQUEST || response.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    deleteSession()
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideApolloClient(
        client: OkHttpClient,
        cache: NormalizedCacheFactory,
        @AnilistTokenInterceptor anilistTokenInterceptor: HttpInterceptor,
        @SessionInterceptor sessionInterceptor: HttpInterceptor
    ): ApolloClient {
        val cacheKeyGenerator = object : CacheKeyGenerator {
            override fun cacheKeyForObject(
                obj: Map<String, Any?>,
                context: CacheKeyGeneratorContext,
            ): CacheKey? = if (
                obj[CACHE_ID_KEY] != null &&
                obj[CACHE_TYPE_KEY] != null
            ) {
                CacheKey(obj[CACHE_TYPE_KEY].toString(), obj[CACHE_ID_KEY].toString())
            } else {
                null
            }
        }

        return ApolloClient.Builder()
            .serverUrl(ANILIST_BASE_URL)
            .addHttpInterceptor(anilistTokenInterceptor)
            .addHttpInterceptor(sessionInterceptor)
            .fetchPolicy(FetchPolicy.CacheAndNetwork)
            .okHttpClient(client)
            .sentryTracing()
            .normalizedCache(
                normalizedCacheFactory = cache,
                cacheKeyGenerator = cacheKeyGenerator,
                writeToCacheAsynchronously = true
            )
            .build()
    }
}