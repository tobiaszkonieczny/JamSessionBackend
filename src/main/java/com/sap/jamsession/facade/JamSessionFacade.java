package com.sap.jamsession.facade;

import com.sap.jamsession.dtos.JamSessionDto;
import com.sap.jamsession.dtos.MessageCreateDto;
import com.sap.jamsession.dtos.MessageDto;
import com.sap.jamsession.exceptions.UnauthorizedException;
import com.sap.jamsession.mappers.JamSessionMapper;
import com.sap.jamsession.mappers.MessageMapper;
import com.sap.jamsession.model.*;
import com.sap.jamsession.services.ImageService;
import com.sap.jamsession.services.JamSessionService;
import com.sap.jamsession.services.MessageService;
import com.sap.jamsession.services.UserService;
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

    // Dodaliśmy parametr parentId
    public MessageDto addCommentToJamSession(int jamSessionId, MessageCreateDto dto, MultipartFile image, Integer parentId) {
        JamSession session = jamSessionService.getById(jamSessionId);
        User user = userService.getCurrentUser();

        ImageData imageData = null;
        if (image != null) {
            imageData = imageService.uploadImage(image);
        }


        Message message = messageService.createMessage(dto, session, user, imageData, parentId);
        if (parentId == null) {
            session.getMessages().add(message);
            jamSessionService.save(session);
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
