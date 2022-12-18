@file:Suppress("UnstableApiUsage")

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

rootProject.name = "Tsuki"

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.gradle.enterprise") version "3.12"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.3.0"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

include(":app")

listOf<String>("common", "core", "data/preferences", "data/remote", "domain", "ui").forEach { dir ->
    rootDir.resolve(dir).walkTopDown().maxDepth(1).filter { file ->
        file.isDirectory && file.resolve("build.gradle.kts").exists()
    }.forEach { module ->
        include(":${dir.replace('/', ':')}:${module.name}")
    }
}

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
