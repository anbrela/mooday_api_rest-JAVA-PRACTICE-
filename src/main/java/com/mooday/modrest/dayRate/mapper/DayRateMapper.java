package com.mooday.modrest.dayRate.mapper;

import com.mooday.modrest.dayRate.DayRate;
import com.mooday.modrest.dayRate.dto.CreateDayRateDto;
import com.mooday.modrest.tag.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DayRateMapper {

    DayRateMapper INSTANCE = Mappers.getMapper(DayRateMapper.class);

    @Mapping(target = "tags", source = "tags")
    DayRate createDayRateDtoToDayRate(CreateDayRateDto createDayRateDto);

    @Mapping(target = "tags", source = "tags")
    CreateDayRateDto dayRateToCreateDayRateDto(DayRate dayRate);

    default Set<Tag> mapTagIdsToTags(List<Integer> tagIds) {
        return tagIds.stream().map(id -> {
            Tag tag = new Tag();
            tag.setId(id.longValue());
            return tag;
        }).collect(Collectors.toSet());
    }

    default List<Integer> mapTagsToTagIds(Set<Tag> tags) {
        return tags.stream().map(tag -> tag.getId().intValue()).collect(Collectors.toList());
    }
}