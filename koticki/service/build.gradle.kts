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
    mainClass.value("ru.itmo.CatApplication")
}
dependencies {
    implementation(project(":koticki:dal"))
    implementation("org.modelmapper:modelmapper:2.3.8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")

    implementation("org.hibernate:hibernate-core:5.5.6.Final")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.0.6")

    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}