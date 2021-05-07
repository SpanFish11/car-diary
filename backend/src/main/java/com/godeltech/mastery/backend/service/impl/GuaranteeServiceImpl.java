package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.request.GuaranteeCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.GuaranteeDTO;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Guarantee;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.GuaranteeMapper;
import com.godeltech.mastery.backend.repository.CarRepository;
import com.godeltech.mastery.backend.repository.GuaranteeRepository;
import com.godeltech.mastery.backend.service.GuaranteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuaranteeServiceImpl implements GuaranteeService {

  private final GuaranteeRepository guaranteeRepository;
  private final CarRepository carRepository;
  private final GuaranteeMapper guaranteeMapper;

  private static final Integer EXTENDED_YEAR = 5;
  private static final Integer REGULAR_YEAR = 3;

  @Override
  public Long createGuarantee(
      final Long carId, final GuaranteeCreateRequest guaranteeCreateRequest) {
    final var car = getCarById(carId);
    final Guarantee guarantee = guaranteeMapper.toEntity(guaranteeCreateRequest);
    if (guarantee.getExtended()) {
      guarantee.setEnd(guarantee.getStart().plusYears(EXTENDED_YEAR));
    } else {
      guarantee.setEnd(guarantee.getStart().plusYears(REGULAR_YEAR));
    }
    guarantee.setCar(car);
    return guaranteeRepository.save(guarantee).getId();
  }

  @Override
  public GuaranteeDTO getGuarantee(final Long carId) {
    Guarantee guarantee =
        guaranteeRepository
            .findByCar(getCarById(carId))
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Could not find any guarantee with car id = " + carId));
    return guaranteeMapper.toDTO(guarantee);
  }

  private Car getCarById(final Long id) {
    return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("car", id));
  }
}
