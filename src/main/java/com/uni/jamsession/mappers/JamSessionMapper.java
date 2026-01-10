package com.sap.jamsession.mappers;

import com.sap.jamsession.model.JamSession;
import com.sap.jamsession.dtos.JamSessionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JamSessionMapper {

  @Mapping(source = "owner", target = "ownerDto")
  public JamSessionDto jamSessionToJamSessionDto(JamSession jamSession);
}
