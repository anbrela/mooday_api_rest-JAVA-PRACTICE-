package com.mooday.modrest.userConfig.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class CreateUserConfigDto {
    @NotNull(message = "Check time is mandatory")
    private String checkTime;

    private List<Long> tagIds;

}
