package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.repository.ModelRepository;
import com.godeltech.mastery.backend.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

  private final ModelRepository modelRepository;

  @Override
  public Model getModelById(final Long id) {
    return modelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("model", id));
  }
}
