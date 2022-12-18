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


package no.kij.tsuki.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskContainer
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


private val Project.libs get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.catalogVersion(alias: String) = libs.findVersion(alias).get().toString()
fun Project.catalogLib(alias: String) = libs.findLibrary(alias).get()
fun Project.catalogBundle(alias: String) = libs.findBundle(alias).get()

fun DependencyHandlerScope.implementation(provider: Provider<*>) =
    addProvider("implementation", provider)

fun DependencyHandlerScope.debugImplementation(provider: Provider<*>) =
    addProvider("debugImplementation", provider)

fun DependencyHandlerScope.kapt(provider: Provider<*>) =
    addProvider("kapt", provider)

fun DependencyHandlerScope.ksp(provider: Provider<*>) =
    addProvider("ksp", provider)

fun DependencyHandlerScope.desugar(provider: Provider<*>) =
    addProvider("coreLibraryDesugaring", provider)

fun ExtensionContainer.commonExtensions() {
    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(TsukiConfiguration.jvmTarget))
            vendor.set(JvmVendorSpec.AZUL)
        }
    }

    configure<KaptExtension> {
        correctErrorTypes = true
    }
}

fun TaskContainer.commonTasks() {
    withType<KotlinCompile>().configureEach {
        kotlinOptions.configureKotlin()
    }
}

private fun KotlinJvmOptions.configureKotlin() {
    jvmTarget = TsukiConfiguration.jvmTarget
    apiVersion = TsukiConfiguration.kotlinVersion
    languageVersion = TsukiConfiguration.kotlinVersion
    freeCompilerArgs = freeCompilerArgs + listOf("-opt-in=kotlin.RequiresOptIn")
}