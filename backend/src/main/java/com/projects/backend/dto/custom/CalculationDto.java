package com.projects.backend.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalculationDto {

    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal finalAmount;

}