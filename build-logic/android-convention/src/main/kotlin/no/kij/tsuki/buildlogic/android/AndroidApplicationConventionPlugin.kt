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

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import no.kij.tsuki.buildlogic.ConventionPlugin
import no.kij.tsuki.buildlogic.TsukiConfiguration
import no.kij.tsuki.buildlogic.catalogBundle
import no.kij.tsuki.buildlogic.catalogLib
import no.kij.tsuki.buildlogic.commonTasks
import no.kij.tsuki.buildlogic.desugar
import no.kij.tsuki.buildlogic.implementation
import no.kij.tsuki.buildlogic.kapt
import no.kij.tsuki.buildlogic.projectImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.io.FileInputStream
import java.util.Properties

internal class AndroidApplicationConventionPlugin : ConventionPlugin {
    override fun Project.configure() {
        apply(plugin = "com.android.application")
        apply(plugin = "org.jetbrains.kotlin.android")
        apply(plugin = "org.jetbrains.kotlin.kapt")
        apply(plugin = "com.google.dagger.hilt.android")
        apply(plugin = "tsuki.sonarqube.android")

        with(extensions) {
            commonAndroidExtensions()
            getByType<BaseAppModuleExtension>().configureApp(project)
        }
        tasks.commonTasks()
        dependencies {
            implementation(platform(catalogLib("compose-bom")))
            implementation(catalogBundle("common-android"))
            implementation(catalogBundle("app"))
            kapt(catalogBundle("kapt-ui"))
            desugar(catalogLib("desugaring"))
            projectImplementation(":core:logging")
        }
    }

    @Suppress("StringLiteralDuplication", "UnstableApiUsage")
    private fun BaseAppModuleExtension.configureApp(project: Project) {
        val rootProject = project.rootProject

        baseAndroidConfig()
        project.configureCompose(this)

        namespace = TsukiConfiguration.packageName
        defaultConfig {
            applicationId = TsukiConfiguration.packageName
            versionName = TsukiConfiguration.versionName
            versionCode = TsukiConfiguration.versionCode
        }

        signingConfigs {
            create("release") {
                val props = Properties().also { prop ->
                    runCatching {
                        FileInputStream(rootProject.file("local.properties")).use { file ->
                            prop.load(file)
                        }
                    }
                }

                enableV3Signing = true
                enableV4Signing = true

                keyAlias = props.getValue("signingAlias", "SIGNING_ALIAS")
                keyPassword = props.getValue("signingAliasPwd", "SIGNING_ALIAS_PWD")
                storeFile = props.getValue("signingFile", "SIGNING_FILE")?.let {
                    rootProject.file(it)
                }
                storePassword = props.getValue("signingFilePwd", "SIGNING_FILE_PWD")
            }
        }

        buildFeatures.buildConfig = true

        buildTypes {
            debug {
                applicationIdSuffix = ".dev"
                versionNameSuffix = "-dev"
                configure(isDebug = true)
            }

            release {
                configure(isDebug = false)
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
                signingConfig = signingConfigs.getByName("release")
            }

            create("beta") {
                initWith(getByName("release"))
                matchingFallbacks.add("release")

                applicationIdSuffix = ".beta"
                versionNameSuffix = "-beta"
            }
        }

        lint {
            abortOnError = false
        }

        packagingOptions {
            resources {
                excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            }
        }
    }

    @Suppress("UnstableApiUsage")
    private fun ApplicationBuildType.configure(isDebug: Boolean) {
        isDebuggable = isDebug
        isDefault = isDebug
        isMinifyEnabled = !isDebug
        isShrinkResources = !isDebug
    }

    private fun Properties.getValue(key: String, env: String) =
        getOrElse(key) { System.getenv(env) } as? String
}