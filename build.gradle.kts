plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    id("org.jetbrains.intellij.platform") version "2.1.0"
}

group = "com.ccterminal"
version = "1.0.1"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.2")
        bundledPlugin("org.jetbrains.plugins.terminal")
    }
}

intellijPlatform {
    instrumentCode = false
    buildSearchableOptions = false

    pluginConfiguration {
        id.set("com.ccterminal")
        name.set("CC Terminal")
        version.set(project.version.toString())
        description.set("A super-lightweight terminal plugin for Claude Code with rich text input support.")
        vendor {
            name.set("xpc1024")
            url.set("https://github.com/xpc1024/CC-Terminal")
        }
        ideaVersion {
            sinceBuild.set("223")
            untilBuild.set("261.*")
        }
    }
}
