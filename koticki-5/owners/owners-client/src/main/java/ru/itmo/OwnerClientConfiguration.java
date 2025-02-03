package ru.itmo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OwnerWebConfig.class, WebOwnersClientImpl.class})
public class OwnerClientConfiguration {
}
