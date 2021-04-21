package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.domain.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CarMapper {

  @Mappings({
    @Mapping(target = "year", source = "year"),
    @Mapping(target = "vin", source = "vin"),
    @Mapping(target = "mileage", source = "mileage")
  })
  Car map(CarCreateRequest carCreateRequest);

  @Mappings({
    @Mapping(target = "model", source = "model"),
    @Mapping(target = "brand", source = "brand"),
    @Mapping(target = "year", source = "year"),
    @Mapping(target = "photoUrl", source = "photoUrl"),
    @Mapping(target = "vin", source = "vin"),
    @Mapping(target = "mileage", source = "mileage")
  })
  CarDTO map(Car car);
}
