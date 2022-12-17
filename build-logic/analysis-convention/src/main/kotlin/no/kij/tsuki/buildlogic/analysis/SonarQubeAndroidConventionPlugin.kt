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
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.sonarqube.gradle.SonarExtension

internal class SonarQubeAndroidConventionPlugin : ConventionPlugin {
    override fun Project.configure() {
        extensions.configure<SonarExtension> {
            properties {
                addFileIfExists("sonar.android.lint.report", "$buildDir/reports/lint-results-debug.xml")
                addFileIfExists("sonar.java.binaries", "$buildDir/tmp/kotlin-classes/debug")
                addFileIfExists("sonar.junit.reportPaths", "$buildDir/test-results/testDebugUnitTest")
                addFileIfExists("sonar.sources", "$projectDir/src/main/kotlin")
                addFileIfExists("sonar.tests", "$projectDir/src/test/kotlin")
            }
        }
    }
}