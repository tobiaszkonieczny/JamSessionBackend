package com.sap.jamsession.mappers;

import com.sap.jamsession.dtos.CreateInstrumentAndRatingDto;
import com.sap.jamsession.model.InstrumentAndRating;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-09T21:32:18+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 21.0.9 (SAP SE)"
)
@Component
public class InstrumentAndRatingMapperImpl implements InstrumentAndRatingMapper {

    @Autowired
    private InstrumentQualifier instrumentQualifier;

    @Override
    public InstrumentAndRating instrumentAndRatingDtotoInstrumentAndRating(CreateInstrumentAndRatingDto createInstrumentAndRatingDto) {
        if ( createInstrumentAndRatingDto == null ) {
            return null;
        }

        InstrumentAndRating instrumentAndRating = new InstrumentAndRating();

        instrumentAndRating.setInstrument( instrumentQualifier.getInstruments( createInstrumentAndRatingDto.instrumentId() ) );
        instrumentAndRating.setRating( createInstrumentAndRatingDto.rating() );

        return instrumentAndRating;
    }
}
