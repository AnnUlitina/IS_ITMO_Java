package ru.itmo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.itmo.model.Cat;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long>, JpaSpecificationExecutor<Cat> {
    Cat findByIdAndOwnerId(Long id, Long catOwnerId);
}
