package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.MusicGenreDto;
import com.uni.jamsession.dtos.UserDto;
import com.uni.jamsession.dtos.instrument.InstrumentDto;
import com.uni.jamsession.dtos.instrumentandrating.InstrumentAndRatingDto;
import com.uni.jamsession.dtos.jamsession.JamSessionDto;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.model.InstrumentAndRating;
import com.uni.jamsession.model.JamSession;
import com.uni.jamsession.model.Location;
import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-11T02:34:49+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 21.0.9 (SAP SE)"
)
@Component
public class JamSessionMapperImpl implements JamSessionMapper {

    @Autowired
    private InstrumentAndRatingMapper instrumentAndRatingMapper;
    @Autowired
    private InstrumentMapper instrumentMapper;

    @Override
    public JamSessionDto jamSessionToJamSessionDto(JamSession jamSession) {
        if ( jamSession == null ) {
            return null;
        }

        UserDto ownerDto = null;
        Integer id = null;
        List<InstrumentAndRatingDto> confirmedInstruments = null;
        LocalDateTime startTime = null;
        Location location = null;
        List<InstrumentDto> requiredInstruments = null;
        MusicGenreDto musicGenre = null;

        ownerDto = userToUserDto( jamSession.getOwner() );
        id = jamSession.getId();
        confirmedInstruments = instrumentAndRatingListToInstrumentAndRatingDtoList( jamSession.getConfirmedInstruments() );
        startTime = jamSession.getStartTime();
        location = jamSession.getLocation();
        requiredInstruments = instrumentListToInstrumentDtoList( jamSession.getRequiredInstruments() );
        musicGenre = musicGenreToMusicGenreDto( jamSession.getMusicGenre() );

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

    protected List<InstrumentAndRatingDto> instrumentAndRatingListToInstrumentAndRatingDtoList(List<InstrumentAndRating> list) {
        if ( list == null ) {
            return null;
        }

        List<InstrumentAndRatingDto> list1 = new ArrayList<InstrumentAndRatingDto>( list.size() );
        for ( InstrumentAndRating instrumentAndRating : list ) {
            list1.add( instrumentAndRatingMapper.toDto( instrumentAndRating ) );
        }

        return list1;
    }

    protected List<InstrumentDto> instrumentListToInstrumentDtoList(List<Instrument> list) {
        if ( list == null ) {
            return null;
        }

        List<InstrumentDto> list1 = new ArrayList<InstrumentDto>( list.size() );
        for ( Instrument instrument : list ) {
            list1.add( instrumentMapper.toDto( instrument ) );
        }

        return list1;
    }

    protected MusicGenreDto musicGenreToMusicGenreDto(MusicGenre musicGenre) {
        if ( musicGenre == null ) {
            return null;
        }

        Integer id = null;
        String name = null;

        id = musicGenre.getId();
        name = musicGenre.getName();

        MusicGenreDto musicGenreDto = new MusicGenreDto( id, name );

        return musicGenreDto;
    }
}
