pluginManagement {
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
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "MyAppSHMR25"
include(":app")
include(":common")
include(":feature:account")
include(":feature:categories")
include(":feature:expenses")
include(":feature:history")
include(":feature:incomes")
include(":feature:splash")
include(":feature:options")
include(":feature:login")
include(":feature:schedule")
