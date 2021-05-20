package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.request.GuaranteeCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.GuaranteeDTO;

public interface GuaranteeService {

  Long createGuarantee(Long carId, GuaranteeCreateRequest guaranteeCreateRequest);

  GuaranteeDTO getGuarantee(Long carId);

  GuaranteeDTO extensionGuarantee(Long carId);
}
