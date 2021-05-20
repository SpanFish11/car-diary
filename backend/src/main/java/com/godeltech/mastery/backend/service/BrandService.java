package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import java.util.List;

public interface BrandService {

  Brand getById(Long id);

  List<BrandDTO> getAllBrands();

  List<ModelDTO> getModelsByBrandId(Long id);
}
