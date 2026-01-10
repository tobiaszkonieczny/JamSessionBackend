package com.sap.jamsession.facade;

import com.sap.jamsession.dtos.MessageDto;
import com.sap.jamsession.mappers.MessageMapper;
import com.sap.jamsession.model.JamSession;
import com.sap.jamsession.model.Message;
import com.sap.jamsession.model.ReactionType;
import com.sap.jamsession.model.User;
import com.sap.jamsession.services.JamSessionService;
import com.sap.jamsession.services.MessageService;
import com.sap.jamsession.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageFacade {
    private final MessageService messageService;
    private final JamSessionService jamSessionService;
    private final MessageMapper messageMapper;
    private final UserService userService;

    public Set<MessageDto> getCommentsForJamSession(int jamSessionId) {
        JamSession jamSession = jamSessionService.getById(jamSessionId);
        return messageService.getCommentsTreeForJamSession(jamSession.getId());
    }

    public MessageDto reactToMessage(int messageId, ReactionType type) {
        User user = userService.getCurrentUser();
        return messageMapper.toDto(messageService.toggleReaction(messageId, user, type));
    }


}
