package com.godeltech.mastery.backend.service.impl;

import static java.util.stream.Collectors.toList;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.BrandMapper;
import com.godeltech.mastery.backend.mapper.ModelMapper;
import com.godeltech.mastery.backend.repository.BrandRepository;
import com.godeltech.mastery.backend.service.BrandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    return getById(id).getModels().stream().map(modelMapper::map).collect(toList());
  }
}
