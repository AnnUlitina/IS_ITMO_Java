package ru.itmo.model;

import itmo.cats.KotickColor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthday;

    private String breed;

    @Enumerated(EnumType.STRING)
    private KotickColor color;

    private Long ownerId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "catsFriends", joinColumns = @JoinColumn(name = "catFriend_first"),
            inverseJoinColumns = @JoinColumn(name = "catFriend_second"))
    private List<Cat> catsFriends = new ArrayList<>();

    public void addFriend(Cat friend) {
        catsFriends.add(friend);
    }

    public void dropFriend(Cat friend) {
        catsFriends.remove(friend);
    }
}
