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

@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

import no.kij.tsuki.buildlogic.TsukiConfiguration
import no.kij.tsuki.buildlogic.TsukiConfiguration.packageName

plugins {
    id("tsuki.android.library")
    alias(libs.plugins.apollo)
}

val pkg = "${TsukiConfiguration.packageName}.data.remote.base"

android {
    namespace = pkg
    buildFeatures.buildConfig = true
}

apollo {
    service("anilist") {
        alwaysGenerateTypesMatching.set(listOf("Query", "User"))
        generateApolloMetadata.set(true)
        packageName.set(pkg)

        introspection {
            endpointUrl.set("https://graphql.anilist.co")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.domain.auth)
    implementation(libs.bundles.data.remote)
    implementation(libs.apollo.cache.sql)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logger)
    implementation(libs.sentry.apollo)
}