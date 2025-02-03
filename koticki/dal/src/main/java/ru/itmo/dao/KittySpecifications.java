package ru.itmo.dao;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.itmo.model.Cat;
import ru.itmo.model.Filter;

import java.util.ArrayList;
import java.util.List;

public class KittySpecifications {
    public static Specification<Cat> byFilter(Filter filter) {
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
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
