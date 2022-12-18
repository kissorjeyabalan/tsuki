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

package no.kij.tsuki.data.remote.base.failure

import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.ApolloParseException
import com.apollographql.apollo3.exception.CacheMissException
import com.apollographql.apollo3.exception.HttpCacheMissException
import com.apollographql.apollo3.exception.JsonDataException
import com.apollographql.apollo3.exception.JsonEncodingException
import no.kij.tsuki.domain.base.failure.Failure

sealed interface CommonRemoteFailure : Failure {
    object Cache : CommonRemoteFailure
    object Network : CommonRemoteFailure
    object Response : CommonRemoteFailure
    object Unknown : CommonRemoteFailure

    companion object {
        operator fun invoke(ex: Throwable) = when (ex) {
            is ApolloHttpException -> Network
            is ApolloNetworkException -> Network
            is CacheMissException -> Cache
            is HttpCacheMissException -> Cache
            is ApolloParseException -> Response
            is JsonDataException -> Response
            is JsonEncodingException -> Response
            else -> Unknown
        }
    }
}