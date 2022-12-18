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

package no.kij.tsuki.data.remote.user

import arrow.core.Either
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import no.kij.tsuki.data.remote.base.failure.CommonRemoteFailure
import no.kij.tsuki.data.remote.user.mapper.response.invoke
import no.kij.tsuki.domain.base.failure.Failure
import no.kij.tsuki.domain.base.logger.Logger
import no.kij.tsuki.domain.user.failure.UserFailure
import javax.inject.Inject

internal class UserRemoteSourceImpl @Inject constructor(
    private val client: ApolloClient,
    private val logger: Logger
) : UserRemoteSource {
    override suspend fun getUserId() = Either.catch(
        f = { client.query(UserIdQuery()).fetchPolicy(FetchPolicy.CacheOnly).execute().data() },
        fe = { error ->
            logger.e(error, "Failed getting userId")
            UserFailure.GettingUserId
        }
    )

    override suspend fun saveUserId() = Either.catch(
        f = { client.query(UserIdQuery()).execute()},
        fe = {error ->
            logger.e(error, "Failed saving userId")
            when (CommonRemoteFailure(error)) {
                CommonRemoteFailure.Cache -> Failure.Unknown
                CommonRemoteFailure.Network -> UserFailure.FetchingUser
                CommonRemoteFailure.Response -> UserFailure.SavingUser
                CommonRemoteFailure.Unknown -> Failure.Unknown
            }
        }
    ).void()
}