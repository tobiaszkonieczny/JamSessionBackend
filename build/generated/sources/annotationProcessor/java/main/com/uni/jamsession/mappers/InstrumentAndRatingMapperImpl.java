package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.InstrumentAndRatingDto;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.model.InstrumentAndRating;
import com.uni.jamsession.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-10T23:38:21+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 21.0.9 (SAP SE)"
)
@Component
public class InstrumentAndRatingMapperImpl implements InstrumentAndRatingMapper {

    @Override
    public InstrumentAndRatingDto toDto(InstrumentAndRating entity) {
        if ( entity == null ) {
            return null;
        }

        int instrumentId = 0;
        String instrumentName = null;
        int userId = 0;
        String name = null;
        int id = 0;
        int rating = 0;

        instrumentId = entityInstrumentId( entity );
        instrumentName = entityInstrumentName( entity );
        Integer id2 = entityUserId( entity );
        if ( id2 != null ) {
            userId = id2;
        }
        name = entityUserName( entity );
        id = entity.getId();
        rating = entity.getRating();

        InstrumentAndRatingDto instrumentAndRatingDto = new InstrumentAndRatingDto( id, rating, instrumentId, instrumentName, userId, name );

        return instrumentAndRatingDto;
    }

    private int entityInstrumentId(InstrumentAndRating instrumentAndRating) {
        Instrument instrument = instrumentAndRating.getInstrument();
        if ( instrument == null ) {
            return 0;
        }
        return instrument.getId();
    }

    private String entityInstrumentName(InstrumentAndRating instrumentAndRating) {
        Instrument instrument = instrumentAndRating.getInstrument();
        if ( instrument == null ) {
            return null;
        }
        return instrument.getName();
    }

    private Integer entityUserId(InstrumentAndRating instrumentAndRating) {
        User user = instrumentAndRating.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String entityUserName(InstrumentAndRating instrumentAndRating) {
        User user = instrumentAndRating.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getName();
    }
}
