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


import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import dagger.hilt.android.plugin.HiltExtension
import no.kij.tsuki.buildlogic.TsukiConfiguration
import no.kij.tsuki.buildlogic.catalogVersion
import no.kij.tsuki.buildlogic.commonExtensions
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.kotlin.dsl.configure

internal fun ExtensionContainer.commonAndroidExtensions() {
    commonExtensions()
    configure<HiltExtension> {
        enableAggregatingTask = true
    }
}

@Suppress("UnstableApiUsage")
internal fun BaseExtension.baseAndroidConfig() {
    compileSdkVersion(TsukiConfiguration.compileSdk)
    buildToolsVersion(TsukiConfiguration.buildTools)

    defaultConfig {
        minSdk = TsukiConfiguration.minSdk
        targetSdk = TsukiConfiguration.targetSdk

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures.buildConfig = false

    buildTypes {
        getByName("release") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            consumerProguardFile("consumer-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = TsukiConfiguration.javaVersion
        targetCompatibility = TsukiConfiguration.javaVersion
        isCoreLibraryDesugaringEnabled = true
    }
}

@Suppress("UnstableApiUsage")
internal fun Project.configureCompose(commonExtension: CommonExtension<*, *, *, *>) {
    with(commonExtension) {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = catalogVersion("compose-compiler")
        }
    }
}