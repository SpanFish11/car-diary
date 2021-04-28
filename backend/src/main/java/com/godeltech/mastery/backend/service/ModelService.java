package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModelService {

    List<ModelDTO> getAllModels();
}
