package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.jamsession.EditJamSessionDto;
import com.uni.jamsession.model.JamSession;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EditJamSessionMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "requiredInstruments", ignore = true)
  @Mapping(target = "confirmedInstruments", ignore = true)
  @Mapping(target = "musicGenre", ignore = true)
  @Mapping(target = "messages", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateJamSessionFromDto(EditJamSessionDto dto, @MappingTarget JamSession jamSession);
}
