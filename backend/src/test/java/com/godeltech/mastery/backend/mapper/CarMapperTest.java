package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.openMocks;

class CarMapperTest {

  @Mock private ModelMapper modelMapper;

  @InjectMocks private final CarMapper carMapper = getMapper(CarMapper.class);

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void testMapCarCreateRequest() {
    final var carCreateRequest =
        CarCreateRequest.builder().year(2019).vin("4Y1SL65848Z411439").mileage(631).build();
    final var expected = Car.builder().year(2019).vin("4Y1SL65848Z411439").mileage(631).build();

    final var actual = carMapper.map(carCreateRequest);

    assertThat(actual, is(expected));
  }

  @Test
  void testMapCarDTO() {
    final var model = new Model(3L, "Curdden", new Brand());
    final var modelDTO = new ModelDTO(3L, "Curdden");
    final var carRequest = Car.builder().id(1L).vin("4Y1SL65848Z411439").model(model).build();
    final var expected = CarDTO.builder().id(1L).vin("4Y1SL65848Z411439").model(modelDTO).build();

    given(modelMapper.map(model)).willReturn(modelDTO);

    final var actual = carMapper.map(carRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(only()).map(model);
  }

  @Test
  void testMapListOfCar() {
    final var model = new Model(3L, "Curdden", new Brand());
    final var modelDTO = new ModelDTO(3L, "Curdden");

    given(modelMapper.map(model)).willReturn(modelDTO);

    final var carsRequest =
        List.of(
            Car.builder().vin("4S3BMHB68B3286050").model(model).build(),
            Car.builder().vin("4Y1SL65848Z411439").model(model).build());
    final var expected =
        List.of(
            CarDTO.builder().vin("4S3BMHB68B3286050").model(modelDTO).build(),
            CarDTO.builder().vin("4Y1SL65848Z411439").model(modelDTO).build());

    final var actual = carMapper.map(carsRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(times(2)).map(model);
  }
}
