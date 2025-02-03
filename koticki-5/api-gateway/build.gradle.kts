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
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
    implementation("org.postgresql:postgresql:42.7.0")
    implementation("org.hibernate:hibernate-core:5.5.6.Final")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.0.6")

    implementation("jakarta.persistence:jakarta.persistence-api:3.0.0")

    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-test")
}

tasks.test {
    useJUnitPlatform()
}