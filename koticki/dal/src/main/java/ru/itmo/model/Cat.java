package ru.itmo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@jakarta.persistence.Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Date birthday;

    private String breed;

    @Enumerated(EnumType.STRING)
    private KotickColor color;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "catsFriends", joinColumns = @JoinColumn(name = "catFriend_first"),
            inverseJoinColumns = @JoinColumn(name = "catFriend_second"))
    private List<Cat> catsFriends = new ArrayList<>();

    public Owner getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }
    public String getBreed() {
        return breed;
    }

    public KotickColor getColor() {
        return color;
    }

    public List<Cat> getCatsFriends() {
        return catsFriends;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addFriend(Cat friend) {
        catsFriends.add(friend);
    }

    public void dropFriend(Cat friend) {
        catsFriends.remove(friend);
    }
}
