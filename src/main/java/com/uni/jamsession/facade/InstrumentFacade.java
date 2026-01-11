package com.uni.jamsession.facade;

import com.uni.jamsession.dtos.instrument.CreateInstrumentDto;
import com.uni.jamsession.dtos.instrument.InstrumentDto;
import com.uni.jamsession.mappers.InstrumentMapper;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.repositories.InstrumentRepository;
import com.uni.jamsession.services.InstrumentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InstrumentFacade {
    private final InstrumentService instrumentService;
    private final InstrumentMapper instrumentMapper;

    public InstrumentDto createInstrument(CreateInstrumentDto createInstrumentDto) {
        Instrument instrument = instrumentService.createInstrument(createInstrumentDto.name());
        return instrumentMapper.toDto(instrument);
    }

    public List<InstrumentDto> getAllInstruments() {
        return instrumentService.findAll().stream()
                .map(instrumentMapper::toDto)
                .toList();
    }

    public void deleteInstrument(int instrumentId) {
        instrumentService.deleteInstrument(instrumentId);
    }
}
