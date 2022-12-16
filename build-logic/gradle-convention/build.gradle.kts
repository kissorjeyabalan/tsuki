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
    `kotlin-dsl`
}

group = "no.kij.tsuki.buildlogic.gradle"

dependencies {
    implementation(project(":common"))
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.sentry)
}

gradlePlugin {
    plugins {
        register("common") {
            id = "tsuki.common"
            implementationClass = "no.kij.tsuki.buildlogic.gradle.CommonConventionPlugin"
        }
        register("sentry") {
            id = "tsuki.sentry"
            implementationClass = "no.kij.tsuki.buildlogic.gradle.SentryConventionPlugin"
        }
    }
}