package ru.itmo.service;

import lombok.experimental.UtilityClass;
import ru.itmo.CatDTO;
import ru.itmo.model.Cat;

import java.util.ArrayList;
import java.util.Objects;

@UtilityClass
public class MappingUtils {
    public static CatDTO mapToCatDto(Cat cat){
        Objects.requireNonNull(cat);
        return new CatDTO(cat.getId(), cat.getName(), cat.getBirthday(), cat.getBreed(), cat.getColor(), cat.getOwnerId());
    }

    public static Cat mapToCatDao(CatDTO cat) {
        Objects.requireNonNull(cat);
        return new Cat(cat.getId(), cat.getName(), cat.getBirthday(), cat.getBreed(), cat.getColor(),
                        null, new ArrayList<>());
    }
}