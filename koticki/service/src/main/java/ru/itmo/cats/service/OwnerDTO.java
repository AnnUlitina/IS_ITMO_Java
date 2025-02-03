package ru.itmo.cats.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;
import java.util.Date;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OwnerDTO {
    int id;
    String name;
    Date birthday;
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }
}