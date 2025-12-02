plugins {
    kotlin("jvm")
    application
}


application {
    mainClass.set("MainKt")
}

dependencies {
    testImplementation(kotlin("test"))
}


