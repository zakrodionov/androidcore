import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.jfrog.bintray")
    id("com.github.dcendents.android-maven")
    id("com.android.library")
    kotlin("android")
}

extra.apply {
    set("bintrayRepo", "AndroidCore")
    set("bintrayName", "com.unitbean.core")
    set("libraryName", "android")

    set("publishedGroupId", "com.unitbean.core")
    set("artifact", "android")
    set("libraryVersion", "1.5.0")

    set("libraryDescription", "Boilerplate Android code for UnitBean developers")
    set("siteUrl", "https://github.com/unitbean/androidcore")
    set("gitUrl", "https://github.com/unitbean/androidcore.git")
    set("developerId", "UnitBean")
    set("developerName", "Android developer")
    set("developerEmail", "pocteg@gmail.com")
    set("licenseName", "The Apache Software License, Version 2.0")
    set("licenseUrl", "http://www.apache.org/licenses/LICENSE-2.0.txt")
    set("allLicenses", arrayOf("Apache-2.0"))
}

repositories {
    mavenCentral()
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(16)
        targetSdkVersion(29)
        versionCode = 1
        versionName = extra.get("libraryVersion") as String
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

val verMoxy = "2.1.1"
val verDagger = "2.26"
val verCoroutines = "1.3.3"
val verRetrofit = "2.6.4"

dependencies {
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    testImplementation("org.mockito:mockito-core:3.2.4")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.robolectric:robolectric:4.3")
    testImplementation("org.robolectric:shadows-support-v4:3.3.2")

    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.2.0-rc01")

    // moxy
    implementation("com.github.moxy-community:moxy:$verMoxy")

    // retrofit 2
    implementation("com.squareup.retrofit2:retrofit:$verRetrofit")

    // rx android
    implementation("io.reactivex.rxjava2:rxjava:2.2.17")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$verCoroutines")
}

/**
 * Создаёт JAR-файл библиотеки в ubutils/libs/jar
 */
task<Copy>("createJar") {
    from("$buildDir/intermediates/intermediate-jars/release/")
    into("libs/jar")
    include("classes.jar")
    rename("classes.jar", "UbUtils.jar")
    exclude("**/BuildConfig.class")
    exclude("**/R.class")
    exclude("**/R$*.class")
}

if (project.rootProject.file("local.properties").exists()) {
    apply("https://raw.githubusercontent.com/nuuneoi/JCenter/master/installv1.gradle")
    apply("https://raw.githubusercontent.com/nuuneoi/JCenter/master/bintrayv1.gradle")
}