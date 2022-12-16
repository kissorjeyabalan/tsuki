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
import com.google.devtools.ksp.gradle.KspExtension
import no.kij.tsuki.buildlogic.ConventionPlugin
import no.kij.tsuki.buildlogic.catalogBundle
import no.kij.tsuki.buildlogic.catalogLib
import no.kij.tsuki.buildlogic.debugImplementation
import no.kij.tsuki.buildlogic.implementation
import no.kij.tsuki.buildlogic.kapt
import no.kij.tsuki.buildlogic.ksp
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal class AndroidComposeConventionPlugin : ConventionPlugin {
    override fun Project.configure() {
        apply(plugin = "tsuki.android.library")
        apply(plugin = "com.google.devtools.ksp")

        with(extensions) {
            configure<KspExtension> {
                arg("compose-destinations.useComposableVisibility", "false")
            }

            configure<LibraryExtension> {
                configureCompose(this)
                libraryVariants.all {
                    sourceSets {
                        getByName(name) {
                            kotlin.srcDir("build/generated/ksp/$name/kotlin")
                        }
                    }
                }
            }
        }

        dependencies {
            implementation(platform(catalogLib("compose-bom")))
            implementation(catalogBundle("ui-compose"))
            debugImplementation(catalogLib("compose-ui-test-manifest"))
            kapt(catalogBundle("kapt-ui"))
            ksp(catalogBundle("ksp-ui"))
        }
    }
}