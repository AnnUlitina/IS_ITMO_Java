package ru.itmo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.model.Owner;

import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
