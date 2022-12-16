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

package no.kij.tsuki.buildlogic.gradle

import io.sentry.android.gradle.extensions.SentryPluginExtension
import no.kij.tsuki.buildlogic.ConventionPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class SentryConventionPlugin : ConventionPlugin {
    override fun Project.configure() {
        apply(plugin = "io.sentry.android.gradle")

        extensions.configure<SentryPluginExtension> {
            autoInstallation.enabled.set(false)
            autoUploadProguardMapping.set(true)
            experimentalGuardsquareSupport.set(false)
            ignoredBuildTypes.set(setOf("debug"))
            includeNativeSources.set(false)
            includeProguardMapping.set(true)
            tracingInstrumentation.enabled.set(false)
            uploadNativeSymbols.set(false)
        }
    }
}