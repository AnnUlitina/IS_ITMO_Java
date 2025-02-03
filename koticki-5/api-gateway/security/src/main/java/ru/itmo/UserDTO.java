package ru.itmo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserDTO {
    Long id;

    String username;

    String password;

    Role roles;

    Long ownerId;
}
