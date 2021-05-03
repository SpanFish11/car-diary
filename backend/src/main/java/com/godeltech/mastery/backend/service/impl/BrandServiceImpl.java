package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.BrandMapper;
import com.godeltech.mastery.backend.mapper.ModelMapper;
import com.godeltech.mastery.backend.repository.BrandRepository;
import com.godeltech.mastery.backend.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

  private final BrandRepository brandRepository;
  private final BrandMapper brandMapper;
  private final ModelMapper modelMapper;

  @Override
  public Brand getById(final Long id) {
    return brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("brand", id));
  }

  @Override
  public List<BrandDTO> getAllBrands() {
    return brandMapper.map(brandRepository.findAll());
  }

  @Override
  public List<ModelDTO> getModelsByBrandId(final Long id) {
    final var models = getById(id).getModels().stream().toList();
    return modelMapper.map(models);
  }
}
