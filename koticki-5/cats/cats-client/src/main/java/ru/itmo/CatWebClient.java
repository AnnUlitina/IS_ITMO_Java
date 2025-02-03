package ru.itmo;

import itmo.cats.KotickColor;

import java.time.LocalDate;
import java.util.List;

public interface CatWebClient {
    CatDTO getById(Long id, Long ownerId);
    CatDTO adminGetById(Long id);
    List<CatDTO> getByParams(List<Long> id, List<String> name, List<LocalDate> birthDay, List<KotickColor> color, List<String> breed, List<Long> ownerId);
    List<CatDTO> adminGetByParams(List<Long> id, List<String> name, List<LocalDate> birthDay, List<KotickColor> color, List<String> breed);
}
