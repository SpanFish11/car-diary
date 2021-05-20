package com.godeltech.mastery.backend.domain.dto.responce;

import java.math.BigDecimal;

public record ServiceWorkDTO(Long id, String name, BigDecimal price, Boolean guarantee) {}
