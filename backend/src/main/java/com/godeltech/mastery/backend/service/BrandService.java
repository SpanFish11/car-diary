package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {

  List<Brand> getAllBrands();

  List<Model> getModelsByBrandId(Long id);
}
