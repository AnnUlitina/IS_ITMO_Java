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
    mainClass.value("ru.itmo.AuthApplication")
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation ("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.slf4j:slf4j-reload4j")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.amqp:spring-amqp")
    implementation("org.springframework.amqp:spring-rabbit")
    implementation("org.springframework.boot:spring-boot-starter-security")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.security:spring-security-core")

    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation(project(":koticki-5:cats:cats-client"))
    implementation(project(":koticki-5:cats:cats-model"))
    implementation(project(":koticki-5:owners:owners-model"))
    implementation(project(":koticki-5:owners:owners-client"))
    implementation(project(":koticki-5:shared:messaging"))
    implementation(project(":koticki-5:api-gateway:security"))
}

tasks.test {
    useJUnitPlatform()
}