package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {

  List<BrandDTO> getAllBrands();

  List<ModelDTO> getModelsByBrandId(Long id);
}
