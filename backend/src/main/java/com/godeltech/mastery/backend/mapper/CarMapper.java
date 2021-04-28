package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.domain.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {ModelMapper.class, BrandMapper.class})
public interface CarMapper {

  @Mapping(target = "year", source = "year")
  @Mapping(target = "vin", source = "vin")
  @Mapping(target = "mileage", source = "mileage")
  Car map(CarCreateRequest carCreateRequest);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "brand", source = "model.brand")
  @Mapping(target = "model", source = "model")
  @Mapping(target = "year", source = "year")
  @Mapping(target = "photoUrl", source = "photoUrl")
  @Mapping(target = "vin", source = "vin")
  @Mapping(target = "mileage", source = "mileage")
  CarDTO map(Car car);

  List<CarDTO> map(List<Car> cars);
}
