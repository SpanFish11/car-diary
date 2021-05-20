package com.godeltech.mastery.backend.domain.dto.responce;

import java.time.LocalDate;

public record GuaranteeDTO(LocalDate start, LocalDate end, Integer mileage, Boolean extended) {}
