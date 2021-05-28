package com.godeltech.mastery.backend.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.godeltech.mastery.backend.domain.dto.request.CarCreateManagerRequest;
import com.godeltech.mastery.backend.domain.dto.request.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
import com.godeltech.mastery.backend.domain.entity.Car;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
      ModelMapper.class,
      BrandMapper.class,
      ClientMapper.class,
      EquipmentMapper.class,
      OperationMapper.class,
      GuaranteeMapper.class
    },
    unmappedTargetPolicy = IGNORE)
public interface CarMapper {

  @Mapping(target = "year", source = "year")
  @Mapping(target = "vin", source = "vin")
  @Mapping(target = "mileage", source = "mileage")
  @Mapping(target = "price", source = "price")
  Car map(CarCreateRequest carCreateRequest);

  @Mapping(target = "year", source = "year")
  @Mapping(target = "vin", source = "vin")
  @Mapping(target = "mileage", source = "mileage")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "ours", source = "ours")
  @Mapping(target = "used", source = "used")
  Car map(CarCreateManagerRequest request);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "brand", source = "model.brand")
  @Mapping(target = "model", source = "model")
  @Mapping(target = "year", source = "year")
  @Mapping(target = "photoUrl", source = "photoUrl")
  @Mapping(target = "vin", source = "vin")
  @Mapping(target = "mileage", source = "mileage")
  @Mapping(target = "ours", source = "ours")
  @Mapping(target = "client", source = "client")
  @Mapping(target = "equipment", source = "equipment")
  @Mapping(target = "used", source = "used")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "guarantee", source = "guarantee")
  @Mapping(target = "serviceOperations", source = "serviceOperations")
  CarDTO map(Car car);

  List<CarDTO> map(List<Car> cars);
}
