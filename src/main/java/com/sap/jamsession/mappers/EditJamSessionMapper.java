package com.sap.jamsession.mappers;

import com.sap.jamsession.model.Instrument;
import com.sap.jamsession.model.InstrumentAndRating;
import com.sap.jamsession.model.JamSession;
import com.sap.jamsession.dtos.EditJamSessionDto;
import com.sap.jamsession.repositories.InstrumentAndRatingRepository;
import com.sap.jamsession.repositories.InstrumentRepository;
import com.sap.jamsession.services.MusicGenreService;
import com.sap.jamsession.services.UserService;

import java.util.List;
import java.util.Set;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface EditJamSessionMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "owner", ignore = true)
  @Mapping(target = "requiredInstruments", ignore = true)
  @Mapping(target = "confirmedInstruments", ignore = true)
  @Mapping(target = "musicGenre", ignore = true)
  @Mapping(target = "messages", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateJamSessionFromDto(EditJamSessionDto dto, @MappingTarget JamSession jamSession);
}
