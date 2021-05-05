package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;

public interface OperationService {

  Long createOperation(Long carId, OperationCreateRequest operation);
}
