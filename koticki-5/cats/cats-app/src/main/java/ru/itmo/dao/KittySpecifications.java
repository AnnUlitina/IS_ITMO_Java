package ru.itmo.dao;

import itmo.cats.Filter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.itmo.model.Cat;

import java.util.ArrayList;
import java.util.List;

public class KittySpecifications {
    public static Specification<Cat> byFilter(Filter filter, String currentUsername) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("name"), filter.getName()));
            }
            if (filter.getBreed() != null && !filter.getBreed().isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("breed"), filter.getBreed()));
            }
            if (filter.getColor() != null) {
                predicates.add(criteriaBuilder.equal(root.get("color"), filter.getColor()));
            }
            predicates.add(criteriaBuilder.equal(root.get("owner").get("name"), currentUsername));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
