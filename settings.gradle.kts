rootProject.name = "newspaper"

pluginManagement {
    repositories {
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}

include("loggin-starter")
include("exception-starter")
include("cloud-config")
include("newspaper-app")
include("user-manager")
