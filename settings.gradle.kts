pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.12.3"
        id("org.jetbrains.kotlin.android") version "2.0.21"
        id("com.google.devtools.ksp") version "2.0.21-1.0.28"
        id("androidx.navigation.safeargs.kotlin") version "2.8.3"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "VivaSegura"
include(":app")
