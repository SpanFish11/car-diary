package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.repository.OperationRepository;
import com.godeltech.mastery.backend.service.OperationService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    @Override
    public Long createOperation(final OperationCreateRequest operation) {
        return null;
    }
}
