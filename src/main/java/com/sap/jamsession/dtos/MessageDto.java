package com.sap.jamsession.dtos;

import com.sap.jamsession.model.ReactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record MessageDto(
        Integer id,
        UserDto author,
        String message,
        String imageUrl,
        LocalDateTime createdAt,
        Integer parentId,
        long reactionCount,
        Map<ReactionType, Long> reactionSummary,
        List<MessageDto> replies
) {}