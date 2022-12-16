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

package no.kij.tsuki.buildlogic

import org.gradle.api.JavaVersion

object TsukiConfiguration {
    const val minSdk = 26
    const val targetSdk = 33
    const val compileSdk = 33
    const val buildTools = "33.0.0"
    const val packageName = "no.kij.tsuki"
    const val versionName = "0.0.1"
    const val versionCode = 1

    val javaVersion = JavaVersion.VERSION_11
    const val jvmTarget = "11"
    const val kotlinVersion = "1.7"
}