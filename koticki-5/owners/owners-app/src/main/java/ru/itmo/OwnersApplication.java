package ru.itmo;

import itmo.MessagingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({
        MessagingConfiguration.class
})
@SpringBootApplication
public class OwnersApplication {
    public static void main(String[] args) {
        SpringApplication.run(OwnersApplication.class, args);
    }
}
