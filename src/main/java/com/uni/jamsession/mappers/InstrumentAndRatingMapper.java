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

  @Mapping(source = "instrumentId", target = "instrument.id")
  @Mapping(source = "instrumentName", target = "instrument.name")
  @Mapping(source = "userId", target = "user.id")
  @Mapping(source = "name", target = "user.name")
    InstrumentAndRating toEntity(InstrumentAndRatingDto dto);
}