package ru.itmo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.itmo.model.Cat;
import ru.itmo.model.KotickColor;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Integer>, JpaSpecificationExecutor<Cat> {
    List<Cat> findCatByColor(KotickColor kotickColor);
    List<Cat> findCatByBreed(String breed);

    List<Cat> findCatByColorAndBreed(KotickColor kotickColor, String breed);
}
