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
    mainClass.value("ru.itmo.CatsApplication")
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.amqp:spring-amqp")
    implementation("org.springframework.amqp:spring-rabbit")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    implementation("org.springframework.boot:spring-boot-starter-security")

//    implementation(project(":koticki-5:cats:cats-client"))
    implementation(project(":koticki-5:cats:cats-model"))
    implementation(project(":koticki-5:shared:messaging"))
}

tasks.test {
    useJUnitPlatform()
}