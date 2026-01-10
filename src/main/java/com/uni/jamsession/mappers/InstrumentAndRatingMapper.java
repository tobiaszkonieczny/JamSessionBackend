package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.InstrumentAndRatingDto;
import com.uni.jamsession.model.InstrumentAndRating;
import com.uni.jamsession.dtos.CreateInstrumentAndRatingDto;
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