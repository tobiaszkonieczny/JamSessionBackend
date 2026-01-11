package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.instrumentandrating.InstrumentAndRatingDto;
import com.uni.jamsession.model.InstrumentAndRating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstrumentAndRatingMapper {

  @Mapping(source = "instrument.id", target = "instrumentId")
  @Mapping(source = "instrument.name", target = "instrumentName")
  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "user.name", target = "name")
  InstrumentAndRatingDto toDto(InstrumentAndRating entity);
}