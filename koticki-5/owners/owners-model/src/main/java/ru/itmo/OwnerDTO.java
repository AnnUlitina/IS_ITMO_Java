package ru.itmo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OwnerDTO {
    Long uuid;
    String name;
    LocalDate birthday;
}