package com.godeltech.mastery.backend.service.impl;

import static java.lang.Boolean.TRUE;

import com.godeltech.mastery.backend.domain.dto.request.GuaranteeCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.GuaranteeDTO;
import com.godeltech.mastery.backend.domain.entity.Guarantee;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.GuaranteeMapper;
import com.godeltech.mastery.backend.repository.GuaranteeRepository;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.service.GuaranteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuaranteeServiceImpl implements GuaranteeService {

  private static final Integer EXTENDED_YEAR = 5;
  private static final Integer REGULAR_YEAR = 3;

  private final GuaranteeRepository guaranteeRepository;
  private final CarService carService;
  private final GuaranteeMapper guaranteeMapper;

  @Override
  public Long createGuarantee(
      final Long carId, final GuaranteeCreateRequest guaranteeCreateRequest) {
    final var car = carService.findCarById(carId);
    if (car.getGuarantee() != null) {
      throw new IllegalArgumentException("Guarantee already exist");
    }
    final var guarantee = guaranteeMapper.toEntity(guaranteeCreateRequest);
    if (TRUE.equals(guarantee.getExtended())) {
      guarantee.setEnd(guarantee.getStart().plusYears(EXTENDED_YEAR));
    } else {
      guarantee.setEnd(guarantee.getStart().plusYears(REGULAR_YEAR));
    }
    guarantee.setCar(car);
    return guaranteeRepository.save(guarantee).getId();
  }

  @Override
  public GuaranteeDTO getGuarantee(final Long carId) {
    final var car = carService.findCarById(carId);
    final Guarantee guarantee =
        guaranteeRepository
            .findByCar(car)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Could not find any guarantee with car id = " + carId));
    return guaranteeMapper.toDTO(guarantee);
  }
}
