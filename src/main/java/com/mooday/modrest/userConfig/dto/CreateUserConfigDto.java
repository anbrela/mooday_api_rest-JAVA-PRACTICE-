package com.mooday.modrest.userConfig.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class CreateUserConfigDto {
    @NotNull(message = "Check time is mandatory")
    private String checkTime;

    @NotNull(message = "Tag IDs are mandatory")
    private List<Long> tagIds;

}
