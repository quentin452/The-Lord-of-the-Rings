import net.minecraftforge.gradle.user.UserExtension
import java.time.LocalDate
import java.time.format.DateTimeFormatter

buildscript {
	repositories {
		mavenCentral()
		maven {
			name = "forge"
			url = uri("https://maven.minecraftforge.net/")
		}
	}
	dependencies {
		classpath("com.anatawa12.forge:ForgeGradle:1.2-1.1.+") {
			isChanging = true
		}
	}
}

apply(plugin = "forge")

plugins {
	id("java")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(8)
	}
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

group = "org.example"
version = "v" + LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd"))

val Project.minecraft: UserExtension
	get() = extensions.getByName<UserExtension>("minecraft")

minecraft.version = "1.7.10-10.13.4.1614-1.7.10"

configure<UserExtension> {
	replacements["@version@"] = project.version
	runDir = "run"
}

tasks {
	jar {
		manifest {
			attributes(
				mapOf(
					"FMLCorePlugin" to "lotr.common.coremod.LOTRLoadingPlugin", "FMLCorePluginContainsFMLMod" to "true"
				)
			)
		}
	}
	withType<JavaCompile> {
		options.encoding = "UTF-8"
	}
}