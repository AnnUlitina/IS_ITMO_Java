package ru.itmo.service.mapping;

import lombok.experimental.UtilityClass;
import ru.itmo.UserDTO;
import ru.itmo.entity.MyUser;

import java.util.Objects;

@UtilityClass
public class MappingUtilsUser {
    public UserDTO asDto(MyUser myUser) {
        Objects.requireNonNull(myUser);
        return new UserDTO(
                myUser.getId(), myUser.getUsername(), myUser.getPassword(), myUser.getRole(), myUser.getOwnerId());
    }
}
