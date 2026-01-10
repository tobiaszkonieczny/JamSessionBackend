package com.sap.jamsession.mappers;

import com.sap.jamsession.dtos.JamSessionDto;
import com.sap.jamsession.dtos.UserDto;
import com.sap.jamsession.model.Instrument;
import com.sap.jamsession.model.InstrumentAndRating;
import com.sap.jamsession.model.JamSession;
import com.sap.jamsession.model.MusicGenre;
import com.sap.jamsession.model.User;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-28T15:11:15+0100",
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

    protected JamSession jamSessionDtoToJamSession(JamSessionDto jamSessionDto) {
        if ( jamSessionDto == null ) {
            return null;
        }

        JamSession jamSession = new JamSession();

        jamSession.setId( jamSessionDto.id() );
        List<InstrumentAndRating> list = jamSessionDto.confirmedInstruments();
        if ( list != null ) {
            jamSession.setConfirmedInstruments( new ArrayList<InstrumentAndRating>( list ) );
        }
        jamSession.setStartTime( jamSessionDto.startTime() );
        jamSession.setLocation( jamSessionDto.location() );
        List<Instrument> list1 = jamSessionDto.requiredInstruments();
        if ( list1 != null ) {
            jamSession.setRequiredInstruments( new ArrayList<Instrument>( list1 ) );
        }
        jamSession.setMusicGenre( jamSessionDto.musicGenre() );

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
