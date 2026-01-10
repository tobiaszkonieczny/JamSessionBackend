package com.uni.jamsession.mappers;

import com.uni.jamsession.model.JamSession;
import com.uni.jamsession.dtos.JamSessionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JamSessionMapper {

  @Mapping(source = "owner", target = "ownerDto")
  public JamSessionDto jamSessionToJamSessionDto(JamSession jamSession);
}
