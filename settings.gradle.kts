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
    }
}

rootProject.name = "MyAppSHMR25"
include(":app")
include(":common")
include(":account")
include(":categories")
include(":expenses")
include(":history")
include(":incomes")
include(":splash")
include(":options")
