package ru.itmo.cats.service;

import lombok.experimental.UtilityClass;
import ru.itmo.model.Cat;
import ru.itmo.model.Owner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class MappingUtils {
    public static CatDTO mapToCatDto(Cat cat){
        Objects.requireNonNull(cat);
        return new CatDTO(cat.getId(), cat.getName(), cat.getBirthday(), cat.getBreed(),
                cat.getColor());
    }

    public static OwnerDTO mapToOwnerDto(Owner owner){
        Objects.requireNonNull(owner);
        return new OwnerDTO(owner.getId(), owner.getName(), owner.getBirthday());
    }

    public static Cat mapToCatDao(CatDTO cat) {
        Objects.requireNonNull(cat);
        return new Cat(cat.getId(), cat.getName(), cat.getBirthday(), cat.getBreed(), cat.getColor(),
                        null, new ArrayList<>());
    }

    public static Owner mapToOwnerDao(OwnerDTO person) {
        Objects.requireNonNull(person);
        return new Owner(person.getId(), person.getName(), person.getBirthday(), new ArrayList<>());
    }
}