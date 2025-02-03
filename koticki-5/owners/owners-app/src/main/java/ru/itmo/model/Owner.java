package ru.itmo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@Table(name = "owners")
public class Owner {
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private LocalDate birthday;
}
