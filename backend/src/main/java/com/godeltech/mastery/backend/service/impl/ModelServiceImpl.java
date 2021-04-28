package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.mapper.ModelMapper;
import com.godeltech.mastery.backend.repository.ModelRepository;
import com.godeltech.mastery.backend.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

  private final ModelRepository modelRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<ModelDTO> getAllModels() {
    return modelMapper.map(modelRepository.findAll());
  }
}
