package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.BrandMapper;
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

  @Override
  public List<BrandDTO> getAllBrands() {
    return brandMapper.map(brandRepository.findAll());
  }

  @Override
  public List<ModelDTO> getModelsByBrandId(final Long id) {
    return brandMapper
        .map(
            brandRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand", id)))
        .getModels()
        .stream()
        .toList();
  }
}
