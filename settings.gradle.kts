pluginManagement {
    repositories { google(); mavenCentral(); gradlePluginPortal() }
}
dependencyResolutionManagement {
    repositories {
        google(); mavenCentral()
        maven("https://api.xposed.info/")
    }
}
rootProject.name = "xposed-coloros-display-settings-fix"
include(":app")
