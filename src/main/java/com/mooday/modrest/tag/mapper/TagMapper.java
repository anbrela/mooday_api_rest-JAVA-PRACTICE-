package com.mooday.modrest.tag.mapper;

import com.mooday.modrest.tag.Tag;
import com.mooday.modrest.tag.dto.CreateTagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    @Mapping(target = "id", ignore = true)  // Ignoramos el campo id porque se genera automáticamente
    Tag createTagDtoToTag(CreateTagDto createTagDto);

    CreateTagDto tagToCreateTagDto(Tag tag);
}
