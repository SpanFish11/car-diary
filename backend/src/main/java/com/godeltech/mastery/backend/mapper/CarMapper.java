package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarMapper {

  // stub
  CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

  @Mappings({
    @Mapping(target = "model", source = "model"),
    @Mapping(target = "brand", source = "brand"),
    @Mapping(target = "year", source = "year"),
    @Mapping(target = "vin", source = "vin"),
    @Mapping(target = "mileage", source = "mileage")
  })
  Car map(CarCreateRequest carCreateRequest);
}
