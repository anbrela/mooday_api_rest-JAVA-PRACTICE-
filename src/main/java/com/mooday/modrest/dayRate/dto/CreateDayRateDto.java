package com.mooday.modrest.dayRate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateDayRateDto {
    @NotNull(message = "Rate is mandatory")
    private Double rate;

    List<Integer> tags;

    private String comment;
}
