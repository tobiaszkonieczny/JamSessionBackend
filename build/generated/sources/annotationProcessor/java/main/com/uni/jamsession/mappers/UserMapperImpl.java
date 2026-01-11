package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.MusicGenreDto;
import com.uni.jamsession.dtos.UserDto;
import com.uni.jamsession.dtos.instrument.InstrumentDto;
import com.uni.jamsession.dtos.instrumentandrating.InstrumentAndRatingDto;
import com.uni.jamsession.dtos.jamsession.JamSessionDto;
import com.uni.jamsession.model.Instrument;
import com.uni.jamsession.model.InstrumentAndRating;
import com.uni.jamsession.model.JamSession;
import com.uni.jamsession.model.MusicGenre;
import com.uni.jamsession.model.User;
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
public class UserMapperImpl extends UserMapper {

    @Autowired
    private JamSessionMapper jamSessionMapper;

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        Set<JamSessionDto> ownedJamSessions = null;
        Integer id = null;
        String name = null;
        String email = null;
        String password = null;
        String bio = null;
        Integer profilePictureId = null;
        Set<MusicGenre> favoriteGenres = null;

        ownedJamSessions = jamSessionSetToJamSessionDtoSet( user.getOwnedJamSessions() );
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();
        bio = user.getBio();
        profilePictureId = user.getProfilePictureId();
        Set<MusicGenre> set1 = user.getFavoriteGenres();
        if ( set1 != null ) {
            favoriteGenres = new LinkedHashSet<MusicGenre>( set1 );
        }

        Set<Integer> favouriteGenreIds = null;

        UserDto userDto = new UserDto( id, name, email, password, bio, profilePictureId, favoriteGenres, favouriteGenreIds, ownedJamSessions );

        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setOwnedJamSessions( jamSessionDtoSetToJamSessionSet( userDto.ownedJamSessions() ) );
        user.setId( userDto.id() );
        user.setName( userDto.name() );
        user.setEmail( userDto.email() );
        user.setPassword( userDto.password() );
        if ( userDto.profilePictureId() != null ) {
            user.setProfilePictureId( userDto.profilePictureId() );
        }
        user.setBio( userDto.bio() );
        Set<MusicGenre> set1 = userDto.favoriteGenres();
        if ( set1 != null ) {
            user.setFavoriteGenres( new LinkedHashSet<MusicGenre>( set1 ) );
        }

        return user;
    }

    @Override
    public void onEditToUser(UserDto userDto, User user) {
        if ( userDto == null ) {
            return;
        }

        if ( user.getFavoriteGenres() != null ) {
            Set<MusicGenre> set = handleFavoriteMusicGenres( userDto.favouriteGenreIds() );
            if ( set != null ) {
                user.getFavoriteGenres().clear();
                user.getFavoriteGenres().addAll( set );
            }
        }
        else {
            Set<MusicGenre> set = handleFavoriteMusicGenres( userDto.favouriteGenreIds() );
            if ( set != null ) {
                user.setFavoriteGenres( set );
            }
        }
        if ( userDto.name() != null ) {
            user.setName( userDto.name() );
        }
        if ( userDto.email() != null ) {
            user.setEmail( userDto.email() );
        }
        if ( userDto.password() != null ) {
            user.setPassword( userDto.password() );
        }
        if ( userDto.bio() != null ) {
            user.setBio( userDto.bio() );
        }
    }

    protected Set<JamSessionDto> jamSessionSetToJamSessionDtoSet(Set<JamSession> set) {
        if ( set == null ) {
            return null;
        }

        Set<JamSessionDto> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
        for ( JamSession jamSession : set ) {
            set1.add( jamSessionMapper.jamSessionToJamSessionDto( jamSession ) );
        }

        return set1;
    }

    protected InstrumentAndRating instrumentAndRatingDtoToInstrumentAndRating(InstrumentAndRatingDto instrumentAndRatingDto) {
        if ( instrumentAndRatingDto == null ) {
            return null;
        }

        InstrumentAndRating instrumentAndRating = new InstrumentAndRating();

        instrumentAndRating.setId( instrumentAndRatingDto.id() );
        instrumentAndRating.setRating( instrumentAndRatingDto.rating() );

        return instrumentAndRating;
    }

    protected List<InstrumentAndRating> instrumentAndRatingDtoListToInstrumentAndRatingList(List<InstrumentAndRatingDto> list) {
        if ( list == null ) {
            return null;
        }

        List<InstrumentAndRating> list1 = new ArrayList<InstrumentAndRating>( list.size() );
        for ( InstrumentAndRatingDto instrumentAndRatingDto : list ) {
            list1.add( instrumentAndRatingDtoToInstrumentAndRating( instrumentAndRatingDto ) );
        }

        return list1;
    }

    protected Instrument instrumentDtoToInstrument(InstrumentDto instrumentDto) {
        if ( instrumentDto == null ) {
            return null;
        }

        Instrument instrument = new Instrument();

        instrument.setId( instrumentDto.id() );
        instrument.setName( instrumentDto.name() );

        return instrument;
    }

    protected List<Instrument> instrumentDtoListToInstrumentList(List<InstrumentDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Instrument> list1 = new ArrayList<Instrument>( list.size() );
        for ( InstrumentDto instrumentDto : list ) {
            list1.add( instrumentDtoToInstrument( instrumentDto ) );
        }

        return list1;
    }

    protected MusicGenre musicGenreDtoToMusicGenre(MusicGenreDto musicGenreDto) {
        if ( musicGenreDto == null ) {
            return null;
        }

        MusicGenre musicGenre = new MusicGenre();

        if ( musicGenreDto.id() != null ) {
            musicGenre.setId( musicGenreDto.id() );
        }
        musicGenre.setName( musicGenreDto.name() );

        return musicGenre;
    }

    protected JamSession jamSessionDtoToJamSession(JamSessionDto jamSessionDto) {
        if ( jamSessionDto == null ) {
            return null;
        }

        JamSession jamSession = new JamSession();

        jamSession.setId( jamSessionDto.id() );
        jamSession.setConfirmedInstruments( instrumentAndRatingDtoListToInstrumentAndRatingList( jamSessionDto.confirmedInstruments() ) );
        jamSession.setStartTime( jamSessionDto.startTime() );
        jamSession.setLocation( jamSessionDto.location() );
        jamSession.setRequiredInstruments( instrumentDtoListToInstrumentList( jamSessionDto.requiredInstruments() ) );
        jamSession.setMusicGenre( musicGenreDtoToMusicGenre( jamSessionDto.musicGenre() ) );

        return jamSession;
    }

    protected Set<JamSession> jamSessionDtoSetToJamSessionSet(Set<JamSessionDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<JamSession> set1 = LinkedHashSet.newLinkedHashSet( set.size() );
        for ( JamSessionDto jamSessionDto : set ) {
            set1.add( jamSessionDtoToJamSession( jamSessionDto ) );
        }

        return set1;
    }
}
