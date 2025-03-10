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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.amqp:spring-amqp")
    implementation("org.springframework.amqp:spring-rabbit")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation(project(":koticki-5:shared:messaging"))
    implementation(project(":koticki-5:owners:owners-model"))
//    implementation(project(":koticki-5:owners:owners-client"))
}

tasks.test {
    useJUnitPlatform()
}