package com.sap.jamsession.mappers;

import com.sap.jamsession.model.InstrumentAndRating;
import com.sap.jamsession.dtos.CreateInstrumentAndRatingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = InstrumentQualifier.class)
public interface InstrumentAndRatingMapper {

  @Mapping(source = "instrumentId", target = "instrument", qualifiedByName = "getInstrument")
  public InstrumentAndRating instrumentAndRatingDtotoInstrumentAndRating(
      CreateInstrumentAndRatingDto createInstrumentAndRatingDto);
}
