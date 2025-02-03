package ru.itmo.cats.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.itmo.model.KotickColor;

import java.sql.Date;

@Value
@AllArgsConstructor
@ToString
@NoArgsConstructor(force = true)
public class CatDTO {
    int id;
    String name;
    Date birthday;
    String breed;
    KotickColor color;
}
