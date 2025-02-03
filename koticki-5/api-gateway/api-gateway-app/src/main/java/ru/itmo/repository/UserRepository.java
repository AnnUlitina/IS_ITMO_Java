package ru.itmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.entity.MyUser;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<MyUser, Long> {
    boolean existsByUsername(String username);
    Optional<MyUser> findUserByUsername(String username);
//    Optional<MyUser> findUserByOwner(Long ownerId);
}
