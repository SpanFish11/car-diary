package com.godeltech.mastery.backend.domain.dto.responce;

import java.math.BigDecimal;

public record MaintenanceOperationDTO(Long id, String name, BigDecimal price) {}
