package ru.itmo;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER");
    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
