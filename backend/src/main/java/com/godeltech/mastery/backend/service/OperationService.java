package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;

public interface OperationService {

  Long createOperation(OperationCreateRequest operation);
}
