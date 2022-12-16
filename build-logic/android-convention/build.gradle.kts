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

group = "no.kij.tsuki.buildlogic.android"

dependencies {
    implementation(project(":common"))
    implementation(libs.gradle.android)
    implementation(libs.gradle.hilt)
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.ksp)
}

gradlePlugin {
    plugins {
        register("android-application") {
            id = "tsuki.android.application"
            implementationClass = "no.kij.tsuki.buildlogic.android.AndroidApplicationConventionPlugin"
        }
        register("android-library") {
            id = "tsuki.android.library"
            implementationClass = "no.kij.tsuki.buildlogic.android.AndroidLibraryConventionPlugin"
        }
        register("android-compose-library") {
            id = "tsuki.android.compose"
            implementationClass = "no.kij.tsuki.buildlogic.android.AndroidComposeConventionPlugin"
        }
    }
}