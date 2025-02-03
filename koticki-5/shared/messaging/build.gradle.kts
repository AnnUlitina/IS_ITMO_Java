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
    mainClass.value("ru.itmo.CatsApplication")
    mainClass.value("ru.itmo.AuthApplication")
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.amqp:spring-rabbit")
    implementation("org.springframework.amqp:spring-amqp")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation ("org.springframework.amqp:spring-rabbit-test")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}