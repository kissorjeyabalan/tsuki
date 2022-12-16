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

import org.sonarqube.gradle.SonarProperties
import java.io.File

internal fun SonarProperties.addFileIfExists(property: String, vararg paths: String) {
    paths.filter { file ->
        File(file).exists()
    }.takeUnless { files ->
        files.isEmpty()
    }?.joinToString(separator = ",")?.let { files ->
        property(property, files)
    }
}