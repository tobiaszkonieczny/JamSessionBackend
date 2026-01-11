package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.instrument.CreateInstrumentDto;
import com.uni.jamsession.dtos.instrument.InstrumentDto;
import com.uni.jamsession.model.Instrument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstrumentMapper {
    public InstrumentDto toDto(Instrument instrument);
}
