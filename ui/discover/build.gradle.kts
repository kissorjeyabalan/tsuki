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

import no.kij.tsuki.buildlogic.TsukiConfiguration

plugins {
    id("tsuki.android.compose")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

android.namespace = "${TsukiConfiguration.packageName}.ui.discover"

ksp {
    arg("compose-destinations.mode", "destinations")
    arg("compose-destinations.moduleName", "discover")
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.domain.discover)
    implementation(projects.ui.base)
}