package com.godeltech.mastery.backend.specification;

import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Car_;
import com.godeltech.mastery.backend.domain.entity.Client_;
import com.godeltech.mastery.backend.domain.entity.Model_;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class CarSpecification {

  private CarSpecification() {}

  public static Specification<Car> withModel(final Long modelId) {
    return (root, criteriaQuery, criteriaBuilder) ->
        Objects.isNull(modelId)
            ? criteriaBuilder.and()
            : criteriaBuilder.equal(root.get(Car_.MODEL).get(Model_.ID), modelId);
  }

  public static Specification<Car> withYear(final Integer year) {
    return (root, criteriaQuery, criteriaBuilder) ->
        Objects.isNull(year)
            ? criteriaBuilder.and()
            : criteriaBuilder.equal(root.get(Car_.YEAR), year);
  }

  public static Specification<Car> greaterOrEqualYear(final Integer year) {
    return (root, criteriaQuery, criteriaBuilder) ->
        Objects.isNull(year)
            ? criteriaBuilder.and()
            : criteriaBuilder.greaterThanOrEqualTo(root.get(Car_.YEAR), year);
  }

  public static Specification<Car> lessOrEqualYear(final Integer year) {
    return (root, criteriaQuery, criteriaBuilder) ->
        Objects.isNull(year)
            ? criteriaBuilder.and()
            : criteriaBuilder.lessThanOrEqualTo(root.get(Car_.YEAR), year);
  }

  public static Specification<Car> withVin(final String vin) {
    return (root, criteriaQuery, criteriaBuilder) ->
        Objects.isNull(vin)
            ? criteriaBuilder.and()
            : criteriaBuilder.like(
                criteriaBuilder.lower(root.get(Car_.VIN)), "%" + vin.toLowerCase() + "%");
  }

  public static Specification<Car> withOwnerLastname(final String lastname) {
    return (root, criteriaQuery, criteriaBuilder) ->
        Objects.isNull(lastname)
            ? criteriaBuilder.and()
            : criteriaBuilder.like(
                criteriaBuilder.lower(root.get(Car_.CLIENT).get(Client_.LAST_NAME)),
                "%" + lastname.toLowerCase() + "%");
  }
}
