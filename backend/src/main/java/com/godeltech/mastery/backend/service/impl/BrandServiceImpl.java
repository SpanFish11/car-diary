package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.repository.BrandRepository;
import com.godeltech.mastery.backend.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

  private final BrandRepository brandRepository;

  @Override
  public List<Brand> getAllBrands() {
    return brandRepository.findAll();
  }

  @Override
  public List<Model> getModelsByBrandId(final Long id) {
    return brandRepository
        .findById(id)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    format("Brand or brand models are not found with brand id %d", id)))
        .getModels()
        .stream()
        .toList();
  }
}
