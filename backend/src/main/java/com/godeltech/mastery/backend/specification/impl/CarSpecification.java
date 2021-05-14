package com.godeltech.mastery.backend.specification.impl;

import com.godeltech.mastery.backend.domain.dto.request.Filter;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Car_;
import com.godeltech.mastery.backend.domain.entity.Client_;
import com.godeltech.mastery.backend.domain.entity.Model_;
import com.godeltech.mastery.backend.specification.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class CarSpecification implements BaseSpecification<Car, Filter> {

  @Override
  public Specification<Car> getFilter(final Filter request) {
    return (root, query, cb) ->
        where(withVin(request.getVin()))
            .and(withOwnerLastname(request.getLastname()))
            .and(withYear(request.getSpecificYear()))
            .and(greaterOrEqualYear(request.getFrom()))
            .and(withModel(request.getModelId()))
            .and(lessOrEqualYear(request.getUntil()))
            .toPredicate(root, query, cb);
  }

  private Specification<Car> withModel(final Long modelId) {
    return (root, query, cb) ->
        isNull(modelId) ? cb.and() : cb.equal(root.get(Car_.MODEL).get(Model_.ID), modelId);
  }

  private Specification<Car> withYear(final Integer year) {
    return (root, query, cb) -> isNull(year) ? cb.and() : cb.equal(root.get(Car_.YEAR), year);
  }

  private Specification<Car> greaterOrEqualYear(final Integer year) {
    return (root, query, cb) ->
        isNull(year) ? cb.and() : cb.greaterThanOrEqualTo(root.get(Car_.YEAR), year);
  }

  private Specification<Car> lessOrEqualYear(final Integer year) {
    return (root, query, cb) ->
        isNull(year) ? cb.and() : cb.lessThanOrEqualTo(root.get(Car_.YEAR), year);
  }

  private Specification<Car> withVin(final String vin) {
    return (root, query, cb) ->
        isNull(vin)
            ? cb.and()
            : cb.like(cb.lower(root.get(Car_.VIN)), "%" + vin.toLowerCase() + "%");
  }

  private Specification<Car> withOwnerLastname(final String lastname) {
    return (root, query, cb) ->
        isNull(lastname)
            ? cb.and()
            : cb.like(
                cb.lower(root.get(Car_.CLIENT).get(Client_.LAST_NAME)),
                "%" + lastname.toLowerCase() + "%");
  }
}
