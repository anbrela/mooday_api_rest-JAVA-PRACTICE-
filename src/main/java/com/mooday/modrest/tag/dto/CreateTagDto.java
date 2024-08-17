package com.mooday.modrest.tag.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTagDto {

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Lang is required")
    private String lang;

    private Long userId;

    private Integer usageCount;

}
