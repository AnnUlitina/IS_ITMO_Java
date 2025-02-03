package ru.itmo.mapping;

import ru.itmo.OwnerDTO;
import ru.itmo.model.Owner;

import java.util.Objects;

public class MappingUtilsOwner {
    public static OwnerDTO mapToOwnerDto(Owner owner){
        Objects.requireNonNull(owner);
        return new OwnerDTO(owner.getId(), owner.getName(), owner.getBirthday());
    }

    public static Owner mapToOwnerDao(OwnerDTO person) {
        Objects.requireNonNull(person);
        return new Owner(person.getUuid(), person.getName(), person.getBirthday());
    }
}
