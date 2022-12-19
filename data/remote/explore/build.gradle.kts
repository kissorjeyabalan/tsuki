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
@file:Suppress("DSL_SCOPE_VIOLATION")
import no.kij.tsuki.buildlogic.TsukiConfiguration
import no.kij.tsuki.buildlogic.desugar

plugins {
    id("tsuki.android.library")
    alias(libs.plugins.apollo)
}

val pkg = "${TsukiConfiguration.packageName}.data.remote.explore"

android.namespace = pkg

apollo {
    service("anilist") {
        generateAsInternal.set(true)
        packageName.set(pkg)
    }
}

dependencies {
    apolloMetadata(projects.data.remote.base)
    implementation(projects.common.core)
    implementation(projects.data.remote.base)
    implementation(projects.domain.explore)
    implementation(libs.bundles.data.remote)
}