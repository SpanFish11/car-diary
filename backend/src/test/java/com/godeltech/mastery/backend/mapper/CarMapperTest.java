package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.openMocks;

class CarMapperTest {

  @Mock private BrandMapper brandMapper;
  @Mock private ModelMapper modelMapper;
  @InjectMocks private CarMapper carMapper = Mappers.getMapper(CarMapper.class);

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void testMapCarCreateRequest() {}

  @Test
  void testMapCarDTO() {
    final Model model = new Model(3L, "Curdden", new Brand());
    final Brand brand = new Brand(1L, "Nissan", Set.of(model));
    final ModelDTO modelDTO = new ModelDTO(3L, "Curdden");
    final BrandDTO brandDTO = new BrandDTO(1L, "Nissan", Set.of(modelDTO));
    final Car carRequest = Car.builder().id(1L).vin("sw31cf52s").model(model).brand(brand).build();
    final CarDTO expected =
        CarDTO.builder().id(1L).vin("sw31cf52s").model(modelDTO).brand(brandDTO).build();

    given(modelMapper.map(model)).willReturn(modelDTO);
    given(brandMapper.map(brand)).willReturn(brandDTO);

    final CarDTO actual = carMapper.map(carRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(only()).map(model);
    then(brandMapper).should(only()).map(brand);
  }

  @Test
  void testMapListOfCar() {}
}
