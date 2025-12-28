package com.sap.jamsession.services;

import com.sap.jamsession.dtos.MessageCreateDto;
import com.sap.jamsession.dtos.MessageDto;
import com.sap.jamsession.exceptions.UnauthorizedException;
import com.sap.jamsession.mappers.MessageMapper;
import com.sap.jamsession.model.*;
import com.sap.jamsession.repositories.MessageRepository;
import com.sap.jamsession.repositories.ReactionRepository;
import com.sap.jamsession.security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    private MessageRepository messageRepository;
    private ReactionRepository reactionRepository;
    private MessageMapper messageMapper;
    private AuthService authService;

    public Message createMessage(MessageCreateDto dto, JamSession session, User user, ImageData image, Integer parentId) {
        Message message = new Message();
        message.setContent(dto.message());
        message.setImage(image);
        message.setUser(user);
        message.setJamSession(session);

        if (parentId != null) {
            Message parent = messageRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
            // Opcjonalnie: blokada odpowiedzi na odpowiedź (tylko 1 poziom)
            if (parent.getParent() != null) {
                throw new IllegalArgumentException("Cannot reply to a reply");
            }
            message.setParent(parent);
        }

        return messageRepository.save(message);
    }

    public Message toggleReaction(int messageId, User user, ReactionType type) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        // Sprawdź czy użytkownik już zareagował
        Optional<Reaction> existing = reactionRepository.findByMessageAndUser(message, user);

        if (existing.isPresent()) {
            if (existing.get().getType() == type) {
                reactionRepository.delete(existing.get());
                return message;
            } else {
                existing.get().setType(type);
                reactionRepository.save(existing.get());
                return message;
            }
        } else {
            Reaction reaction = new Reaction();
            reaction.setMessage(message);
            reaction.setUser(user);
            reaction.setType(type);
            reactionRepository.save(reaction);
            return message;
        }
    }

    public Set<MessageDto> getCommentsTreeForJamSession(int jamSessionId) {
        // Pobieramy tylko wiadomości, które mają parent_id IS NULL
        List<Message> rootComments = messageRepository.findByJamSessionIdAndParentIsNullOrderByCreatedAtDesc(jamSessionId);

        return rootComments.stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toSet());
    }

    public void deleteCommentFromJamSession(int messageId) throws UnauthorizedException {
        if(!authService.isAdmin()){
            throw new UnauthorizedException("Only admins can delete comments.");
        }
        messageRepository.deleteById(messageId);
    }

}
