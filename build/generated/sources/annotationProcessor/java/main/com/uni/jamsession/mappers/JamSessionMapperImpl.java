package com.sap.jamsession.mappers;

import com.sap.jamsession.dtos.JamSessionDto;
import com.sap.jamsession.dtos.UserDto;
import com.sap.jamsession.model.Instrument;
import com.sap.jamsession.model.InstrumentAndRating;
import com.sap.jamsession.model.JamSession;
import com.sap.jamsession.model.Location;
import com.sap.jamsession.model.MusicGenre;
import com.sap.jamsession.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-28T15:11:15+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 21.0.9 (SAP SE)"
)
@Component
public class JamSessionMapperImpl implements JamSessionMapper {

    @Override
    public JamSessionDto jamSessionToJamSessionDto(JamSession jamSession) {
        if ( jamSession == null ) {
            return null;
        }

        UserDto ownerDto = null;
        Integer id = null;
        List<InstrumentAndRating> confirmedInstruments = null;
        LocalDateTime startTime = null;
        Location location = null;
        List<Instrument> requiredInstruments = null;
        MusicGenre musicGenre = null;

        ownerDto = userToUserDto( jamSession.getOwner() );
        id = jamSession.getId();
        List<InstrumentAndRating> list = jamSession.getConfirmedInstruments();
        if ( list != null ) {
            confirmedInstruments = new ArrayList<InstrumentAndRating>( list );
        }
        startTime = jamSession.getStartTime();
        location = jamSession.getLocation();
        List<Instrument> list1 = jamSession.getRequiredInstruments();
        if ( list1 != null ) {
            requiredInstruments = new ArrayList<Instrument>( list1 );
        }
        musicGenre = jamSession.getMusicGenre();

        JamSessionDto jamSessionDto = new JamSessionDto( id, ownerDto, confirmedInstruments, startTime, location, requiredInstruments, musicGenre );

        return jamSessionDto;
    }

    protected Set<JamSessionDto> jamSessionSetToJamSessionDtoSet(Set<JamSession> set) {
        if ( set == null ) {
            return null;
        }

        Set<JamSessionDto> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
        for ( JamSession jamSession : set ) {
            set1.add( jamSessionToJamSessionDto( jamSession ) );
        }

        return set1;
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String email = null;
        String password = null;
        String bio = null;
        Integer profilePictureId = null;
        Set<MusicGenre> favoriteGenres = null;
        Set<JamSessionDto> ownedJamSessions = null;

        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
        bio = user.getBio();
        profilePictureId = user.getProfilePictureId();
        Set<MusicGenre> set = user.getFavoriteGenres();
        if ( set != null ) {
            favoriteGenres = new LinkedHashSet<MusicGenre>( set );
        }
        ownedJamSessions = jamSessionSetToJamSessionDtoSet( user.getOwnedJamSessions() );

        Set<Integer> favouriteGenreIds = null;

        UserDto userDto = new UserDto( id, name, email, password, bio, profilePictureId, favoriteGenres, favouriteGenreIds, ownedJamSessions );

        return userDto;
    }
}
