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

package no.kij.tsuki.buildlogic.android

import com.android.build.gradle.LibraryExtension
import no.kij.tsuki.buildlogic.ConventionPlugin
import no.kij.tsuki.buildlogic.catalogBundle
import no.kij.tsuki.buildlogic.catalogLib
import no.kij.tsuki.buildlogic.commonTasks
import no.kij.tsuki.buildlogic.desugar
import no.kij.tsuki.buildlogic.implementation
import no.kij.tsuki.buildlogic.kapt
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class AndroidLibraryConventionPlugin : ConventionPlugin {
    override fun Project.configure() {
        apply(plugin = "com.android.library")
        apply(plugin = "org.jetbrains.kotlin.android")
        apply(plugin = "org.jetbrains.kotlin.kapt")
        apply(plugin = "com.google.dagger.hilt.android")
        apply(plugin = "tsuki.sonarqube.android")

        with(extensions) {
            commonAndroidExtensions()
            getByType<LibraryExtension>().baseAndroidConfig()
        }

        tasks.commonTasks()

        dependencies {
            implementation(catalogBundle("common-android"))
            kapt(catalogBundle("kapt"))
            desugar(catalogLib("desugaring"))
        }
    }
}