package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.request.CarCreateManagerRequest;
import com.godeltech.mastery.backend.domain.dto.request.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Client;
import com.godeltech.mastery.backend.domain.entity.Equipment;
import com.godeltech.mastery.backend.domain.entity.Model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class CarMapperUnitTest {

  @Mock private BrandMapper brandMapper;
  @Mock private ModelMapper modelMapper;
  @Mock private ClientMapper clientMapper;
  @Mock private EquipmentMapper equipmentMapper;

  @InjectMocks private final CarMapper carMapper = getMapper(CarMapper.class);

  @Test
  void testMapCarCreateRequest() {
    final var carCreateRequest =
        CarCreateRequest.builder()
            .year(2019)
            .vin("4Y1SL65848Z411439")
            .mileage(631)
            .price(new BigDecimal("5000.99"))
            .build();
    final var expected =
        Car.builder()
            .year(2019)
            .vin("4Y1SL65848Z411439")
            .mileage(631)
            .price(new BigDecimal("5000.99"))
            .build();

    final var actual = carMapper.map(carCreateRequest);

    assertThat(actual, is(expected));
  }

  @Test
  void testMapCarCreateManagerRequest() {
    final var carCreateRequest =
        CarCreateManagerRequest.managerRequestBuilder()
            .ours(TRUE)
            .used(FALSE)
            .year(2019)
            .vin("4Y1SL65848Z411439")
            .mileage(631)
            .price(new BigDecimal("5000.99"))
            .build();
    final var expected =
        Car.builder()
            .year(2019)
            .vin("4Y1SL65848Z411439")
            .mileage(631)
            .price(new BigDecimal("5000.99"))
            .ours(TRUE)
            .used(FALSE)
            .build();

    final var actual = carMapper.map(carCreateRequest);

    assertThat(actual, is(expected));
  }

  @Test
  void testMapCarDTO() {
    final var brand = new Brand(1L, "Nissan", null);
    final var model = new Model(3L, "Curdden", brand);
    final var client = new Client(2L, "John", "Snow", "shon344@mail.com", "sdsdsd", null);
    final var equipment = new Equipment(1L, "Luxury", "Petrol", "Manual", 2D, 400);
    final var modelDTO = new ModelDTO(3L, "Curdden");
    final var brandDTO = new BrandDTO(1L, "Nissan");
    final var clientDTO = new ClientDTO(2L, "John", "Snow", "shon344@mail.com");
    final var equipmentDTO = new EquipmentDTO(1L, "Luxury", "Petrol", "Manual", 2D, 400);

    final var carRequest =
        Car.builder()
            .id(1L)
            .vin("4Y1SL65848Z411439")
            .model(model)
            .client(client)
            .equipment(equipment)
            .build();
    final var expected =
        CarDTO.builder()
            .id(1L)
            .vin("4Y1SL65848Z411439")
            .model(modelDTO)
            .brand(brandDTO)
            .client(clientDTO)
            .equipment(equipmentDTO)
            .build();

    given(modelMapper.map(model)).willReturn(modelDTO);
    given(brandMapper.map(brand)).willReturn(brandDTO);
    given(clientMapper.toDto(client)).willReturn(clientDTO);
    given(equipmentMapper.toDto(equipment)).willReturn(equipmentDTO);

    final var actual = carMapper.map(carRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(only()).map(refEq(model));
    then(brandMapper).should(only()).map(refEq(brand));
    then(clientMapper).should(only()).toDto(argThat(client::equals));
    then(equipmentMapper).should(only()).toDto(argThat(equipment::equals));
  }

  @Test
  void testMapListOfCar() {
    final var brand = new Brand(1L, "Nissan", null);
    final var model = new Model(3L, "Curdden", brand);
    final var client = new Client(2L, "John", "Snow", "shon344@mail.com", "sdsdsd", null);
    final var equipment = new Equipment(1L, "Luxury", "Petrol", "Manual", 2D, 400);
    final var modelDTO = new ModelDTO(3L, "Curdden");
    final var brandDTO = new BrandDTO(1L, "Nissan");
    final var clientDTO = new ClientDTO(2L, "John", "Snow", "shon344@mail.com");
    final var equipmentDTO = new EquipmentDTO(1L, "Luxury", "Petrol", "Manual", 2D, 400);

    given(modelMapper.map(model)).willReturn(modelDTO);
    given(brandMapper.map(brand)).willReturn(brandDTO);
    given(clientMapper.toDto(client)).willReturn(clientDTO);
    given(equipmentMapper.toDto(equipment)).willReturn(equipmentDTO);

    final var carsRequest =
        List.of(
            Car.builder()
                .vin("4S3BMHB68B3286050")
                .model(model)
                .equipment(equipment)
                .client(client)
                .build(),
            Car.builder()
                .vin("4Y1SL65848Z411439")
                .model(model)
                .equipment(equipment)
                .client(client)
                .build());
    final var expected =
        List.of(
            CarDTO.builder()
                .vin("4S3BMHB68B3286050")
                .model(modelDTO)
                .brand(brandDTO)
                .equipment(equipmentDTO)
                .client(clientDTO)
                .build(),
            CarDTO.builder()
                .vin("4Y1SL65848Z411439")
                .model(modelDTO)
                .brand(brandDTO)
                .equipment(equipmentDTO)
                .client(clientDTO)
                .build());

    final var actual = carMapper.map(carsRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(times(2)).map(refEq(model));
    then(brandMapper).should(times(2)).map(refEq(brand));
    then(clientMapper).should(times(2)).toDto(argThat(client::equals));
    then(equipmentMapper).should(times(2)).toDto(argThat(equipment::equals));
  }
}
