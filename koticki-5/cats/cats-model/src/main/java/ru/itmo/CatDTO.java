package ru.itmo;

import itmo.cats.KotickColor;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Value
@AllArgsConstructor
@ToString
@Builder
@NoArgsConstructor(force = true)
public class CatDTO {
    Long id;
    String name;
    LocalDate birthday;
    String breed;
    KotickColor color;
    Long ownerId;
}
