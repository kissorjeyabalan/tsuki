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

plugins {
    id("tsuki.android.application")
    id("tsuki.sonarqube.android")
    id("io.sentry.android.gradle") version "3.7.0"
}

sentry {
    autoInstallation.enabled.set(false)
    autoUploadProguardMapping.set(true)
    experimentalGuardsquareSupport.set(false)
    ignoredBuildTypes.set(setOf("debug"))
    includeNativeSources.set(false)
    includeProguardMapping.set(true)
    tracingInstrumentation.enabled.set(false)
    uploadNativeSymbols.set(false)
}

dependencies {
    implementation(projects.core.common)

    implementation(projects.core.logging)

    implementation(projects.data.preferences.base)
    implementation(projects.data.preferences.auth)

    implementation(projects.data.remote.base)
    implementation(projects.data.remote.discover)
    implementation(projects.data.remote.user)

    implementation(projects.domain.base)
    implementation(projects.domain.auth)
    implementation(projects.domain.discover)
    implementation(projects.domain.user)

    implementation(projects.ui.base)
    implementation(projects.ui.discover)
    implementation(projects.ui.login)

    implementation(libs.sentry)

    implementation(libs.timber)
}