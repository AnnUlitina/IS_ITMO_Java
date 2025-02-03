plugins {
    id("java")
    id("io.freefair.lombok") version "6.1.0-m2"
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
springBoot {
    mainClass.value("ru.itmo.OwnersApplication")
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.2.5")
    implementation("org.springframework.boot:spring-boot-starter-web:3.2.5")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.2.5")
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation(project(":koticki-5:owners:owners-model"))
    implementation(project(":koticki-5:shared:messaging"))
}

tasks.test {
    useJUnitPlatform()
}