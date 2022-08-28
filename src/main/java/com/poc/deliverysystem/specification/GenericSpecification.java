package com.poc.deliverysystem.specification;

import com.poc.deliverysystem.model.entity.Company;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class GenericSpecification<T> implements Specification<T> {

    private List<SearchCriteria> list;

    public GenericSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                if (criteria.getValue() instanceof LocalDateTime)
                    predicates.add(criteriaBuilder.greaterThan(root.get(criteria.getKey()).as(LocalDateTime.class), (LocalDateTime) criteria.getValue()));
                else if (criteria.getValue() instanceof Number)
                    predicates.add(criteriaBuilder.greaterThan(root.get(criteria.getKey()).as(Long.class), (Long) criteria.getValue()));
                else
                    predicates.add(criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                if (criteria.getValue() instanceof LocalDateTime)
                    predicates.add(criteriaBuilder.lessThan(root.get(criteria.getKey()).as(LocalDateTime.class), (LocalDateTime) criteria.getValue()));
                else if (criteria.getValue() instanceof Number)
                    predicates.add(criteriaBuilder.lessThan(root.get(criteria.getKey()).as(Long.class), (Long) criteria.getValue()));
                else
                    predicates.add(criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                if (criteria.getValue() instanceof LocalDateTime)
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()).as(LocalDateTime.class), (LocalDateTime) criteria.getValue()));
                else if (criteria.getValue() instanceof Number)
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()).as(Long.class), (Long) criteria.getValue()));
                else
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                if (criteria.getValue() instanceof LocalDateTime)
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()).as(LocalDateTime.class), (LocalDateTime) criteria.getValue()));
                else if (criteria.getValue() instanceof Number)
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()).as(Long.class), (Long) criteria.getValue()));
                else
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                predicates.add(criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                if (criteria.getValue() instanceof Company)
                    predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()).as(Company.class), criteria.getValue()));
                else
                    predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(criteria.getKey())),"%" + criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(criteria.getKey())),criteria.getValue().toString().toLowerCase() + "%"));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
