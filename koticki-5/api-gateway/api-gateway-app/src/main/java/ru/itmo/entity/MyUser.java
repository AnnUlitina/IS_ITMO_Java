package ru.itmo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.Role;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class MyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private Long ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public MyUser(String username, String password, Role role, Long owner) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.ownerId = owner;
    }
}
