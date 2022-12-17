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

package no.kij.tsuki.buildlogic.analysis

import no.kij.tsuki.buildlogic.ConventionPlugin
import no.kij.tsuki.buildlogic.TsukiConfiguration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.sonarqube.gradle.SonarExtension

internal class SonarQubeConventionPlugin : ConventionPlugin {
    override fun Project.configure() {
        apply(plugin = "org.sonarqube")

        extensions.configure<SonarExtension> {
            properties {
                property("sonar.projectKey", "kissorjeyabalan_tsuki")
                property("sonar.organization", "kissorjeyabalan")
                property("sonar.host.url", "https://sonarcloud.io")
                property("sonar.projectName", "tsuki")
                property(
                    "sonar.projectVersion",
                    "${TsukiConfiguration.versionName}_(${TsukiConfiguration.versionCode})"
                )
                property("sonar.kotlin.detekt.reportPaths", "${rootProject.buildDir}/reports/detekt/detekt.xml")
                property("sonar.language", "kotlin")
                property("sonar.log.level", "TRACE")
                property("sonar.qualitygate.wait", true)
                property("sonar.sourceEncoding", "UTF-8")
                property("sonar.tags", "android")
                property("sonar.verbose", true)
            }

            subprojects {
                androidVariant = "debug"
            }
        }
    }
}