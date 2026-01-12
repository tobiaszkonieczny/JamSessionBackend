package com.uni.jamsession.mappers;

import com.uni.jamsession.model.JamSession;
import com.uni.jamsession.dtos.jamsession.JamSessionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        InstrumentAndRatingMapper.class,
        InstrumentMapper.class
})
public interface JamSessionMapper {

  @Mapping(target = "owner", source = "owner")
  JamSessionDto jamSessionToJamSessionDto(JamSession jamSession);
}
