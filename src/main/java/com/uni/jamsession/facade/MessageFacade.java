package com.uni.jamsession.facade;

import com.uni.jamsession.dtos.MessageDto;
import com.uni.jamsession.mappers.MessageMapper;
import com.uni.jamsession.model.JamSession;
import com.uni.jamsession.model.ReactionType;
import com.uni.jamsession.model.User;
import com.uni.jamsession.services.JamSessionService;
import com.uni.jamsession.services.MessageService;
import com.uni.jamsession.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

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
