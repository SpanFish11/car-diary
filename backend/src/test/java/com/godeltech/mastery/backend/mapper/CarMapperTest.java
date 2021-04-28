package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
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
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.openMocks;

class CarMapperTest {

  @Mock private BrandMapper brandMapper;
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
    final var brand = new Brand(1L, "Nissan", Set.of());
    final var model = new Model(3L, "Curdden", brand);
    final var modelDTO = new ModelDTO(3L, "Curdden");
    final var brandDTO = new BrandDTO(1L, "Nissan", Set.of(modelDTO));
    final var carRequest =
            Car.builder().id(1L).vin("4Y1SL65848Z411439").model(model).build();
    final var expected =
            CarDTO.builder().id(1L).vin("4Y1SL65848Z411439").model(modelDTO).brand(brandDTO).build();

    given(modelMapper.map(model)).willReturn(modelDTO);
    given(brandMapper.map(brand)).willReturn(brandDTO);

    final var actual = carMapper.map(carRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(only()).map(model);
    then(brandMapper).should(only()).map(brand);
  }

  @Test
  void testMapListOfCar() {
    final var brand = new Brand(1L, "Nissan", Set.of());
    final var model = new Model(3L, "Curdden", brand);
    final var modelDTO = new ModelDTO(3L, "Curdden");
    final var brandDTO = new BrandDTO(1L, "Nissan", Set.of(modelDTO));

    given(modelMapper.map(model)).willReturn(modelDTO);
    given(brandMapper.map(brand)).willReturn(brandDTO);

    final var carsRequest =
            List.of(
                    Car.builder().vin("4S3BMHB68B3286050").model(model).build(),
                    Car.builder().vin("4Y1SL65848Z411439").model(model).build());
    final var expected =
            List.of(
                    CarDTO.builder().vin("4S3BMHB68B3286050").model(modelDTO).brand(brandDTO).build(),
                    CarDTO.builder().vin("4Y1SL65848Z411439").model(modelDTO).brand(brandDTO).build());

    final var actual = carMapper.map(carsRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(times(2)).map(model);
    then(brandMapper).should(times(2)).map(brand);
  }
}
