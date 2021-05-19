package com.godeltech.mastery.backend.domain.dto.responce;

import java.math.BigDecimal;

public record ChangeablePartDTO(Long id, Boolean replaced, String model, BigDecimal price) {}
