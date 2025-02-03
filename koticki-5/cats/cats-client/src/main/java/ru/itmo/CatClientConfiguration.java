package ru.itmo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CatWebConfig.class, WebCatsClientImpl.class})
public class CatClientConfiguration {
}
