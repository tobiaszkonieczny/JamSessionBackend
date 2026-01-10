package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.EditJamSessionDto;
import com.uni.jamsession.model.JamSession;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-10T23:34:08+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 21.0.9 (SAP SE)"
)
@Component
public class EditJamSessionMapperImpl implements EditJamSessionMapper {

    @Override
    public void updateJamSessionFromDto(EditJamSessionDto dto, JamSession jamSession) {
        if ( dto == null ) {
            return;
        }

        if ( dto.startTime() != null ) {
            jamSession.setStartTime( dto.startTime() );
        }
        if ( dto.location() != null ) {
            jamSession.setLocation( dto.location() );
        }
    }
}
