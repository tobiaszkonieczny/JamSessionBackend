package com.uni.jamsession.facade;
import com.uni.jamsession.dtos.jamsession.CreateJamSessionDto;
import com.uni.jamsession.dtos.jamsession.EditJamSessionDto;
import com.uni.jamsession.dtos.jamsession.JamSessionDto;
import com.uni.jamsession.dtos.MessageCreateDto;
import com.uni.jamsession.dtos.MessageDto;
import com.uni.jamsession.exceptions.UnauthorizedException;
import com.uni.jamsession.mappers.JamSessionMapper;
import com.uni.jamsession.mappers.MessageMapper;
import com.uni.jamsession.model.ImageData;
import com.uni.jamsession.model.JamSession;
import com.uni.jamsession.model.Message;
import com.uni.jamsession.model.User;
import com.uni.jamsession.services.ImageService;
import com.uni.jamsession.services.JamSessionService;
import com.uni.jamsession.services.MessageService;
import com.uni.jamsession.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JamSessionFacade {
    private final JamSessionService jamSessionService;
    private final MessageService messageService;
    private final UserService userService;
    private final ImageService imageService;
    private final MessageMapper messageMapper;
    private final JamSessionMapper jamSessionMapper;

    public JamSessionDto createJamSession(CreateJamSessionDto dto) {
        JamSession jamSession = jamSessionService.createJamSession(dto);
        return jamSessionMapper.jamSessionToJamSessionDto(jamSession);
    }

    public JamSessionDto getJamSessionById(int id) {
        JamSession jamSession = jamSessionService.getById(id);
        return jamSessionMapper.jamSessionToJamSessionDto(jamSession);
    }

    public JamSessionDto editJamSession(EditJamSessionDto dto, int id) throws UnauthorizedException {
        JamSession jamSession = jamSessionService.editJamSession(dto, id);
        return jamSessionMapper.jamSessionToJamSessionDto(jamSession);
    }

    public Set<JamSessionDto> getAllJamSessions() {
        return jamSessionService.getAllJamSessions().stream().map(
                jamSessionMapper::jamSessionToJamSessionDto
        ).collect(Collectors.toSet());
    }

    public void deleteJamSession(int id) throws UnauthorizedException {
        jamSessionService.deleteJamSession(id);
    }

    public void deleteInstrumentFromJamSession(int jamSessionId, int instrumentId) throws UnauthorizedException {
        jamSessionService.deleteInstrumentFromJamSession(jamSessionId, instrumentId);
    }


    public MessageDto addCommentToJamSession(int jamSessionId, MessageCreateDto dto, MultipartFile image, Integer parentId) {
        JamSession session = jamSessionService.getById(jamSessionId);
        User user = userService.getCurrentUser();

        ImageData imageData = null;
        if (image != null) {
            imageData = imageService.saveImage(image);
        }

        Message message = messageService.createMessage(dto, session, user, imageData, parentId);
        if (parentId == null) {
            jamSessionService.addMessageToJamSession(session, message);
        }

        return messageMapper.toDto(message);
    }


    public void deleteCommentFromJamSession(int messageId) throws UnauthorizedException {
        messageService.deleteCommentFromJamSession(messageId);
    }


    public Set<JamSessionDto> getAllJamSessionsByUserId(int userId) {
        return jamSessionService.getAllJamSessionsByUser(userId).stream().map(
                jamSessionMapper::jamSessionToJamSessionDto
        ).collect(Collectors.toSet());
    }

    public Set<JamSessionDto> getAllSignedUpJamSessionsByUserId(int userId) {
        return jamSessionService.getAllSignedUpJamSessionsByUser(userId).stream().map(
                jamSessionMapper::jamSessionToJamSessionDto
        ).collect(Collectors.toSet());
    }

    public JamSessionDto addUserToJamSession(int jamSessionId, int instrumentAndRatingId) {
        var updatedJamSession = jamSessionService.addUserToJamSession(jamSessionId, instrumentAndRatingId);
        return jamSessionMapper.jamSessionToJamSessionDto(updatedJamSession);
    }

    public void removeUserFromJamSession(int jamSessionId, int userId) {
        jamSessionService.removeUserFromJamSession(jamSessionId, userId);
    }

}
