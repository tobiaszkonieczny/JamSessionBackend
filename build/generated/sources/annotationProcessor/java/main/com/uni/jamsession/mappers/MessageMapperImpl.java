package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.MessageDto;
import com.uni.jamsession.dtos.UserDto;
import com.uni.jamsession.model.Message;
import com.uni.jamsession.model.ReactionType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-10T23:34:08+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.11.1.jar, environment: Java 21.0.9 (SAP SE)"
)
@Component
public class MessageMapperImpl extends MessageMapper {

    @Override
    public MessageDto toDto(Message message) {
        if ( message == null ) {
            return null;
        }

        String message1 = null;
        String imageUrl = null;
        UserDto author = null;
        Integer parentId = null;
        Integer id = null;
        LocalDateTime createdAt = null;
        List<MessageDto> replies = null;

        message1 = message.getContent();
        imageUrl = mapImageToUrl( message.getImage() );
        author = mapUserIdToUserDto( message.getUser() );
        parentId = messageParentId( message );
        id = message.getId();
        createdAt = message.getCreatedAt();
        replies = messageListToMessageDtoList( message.getReplies() );

        long reactionCount = Long.valueOf(message.getReactions().size());
        Map<ReactionType, Long> reactionSummary = mapReactions(message.getReactions());

        MessageDto messageDto = new MessageDto( id, author, message1, imageUrl, createdAt, parentId, reactionCount, reactionSummary, replies );

        return messageDto;
    }

    private Integer messageParentId(Message message) {
        Message parent = message.getParent();
        if ( parent == null ) {
            return null;
        }
        return parent.getId();
    }

    protected List<MessageDto> messageListToMessageDtoList(List<Message> list) {
        if ( list == null ) {
            return null;
        }

        List<MessageDto> list1 = new ArrayList<MessageDto>( list.size() );
        for ( Message message : list ) {
            list1.add( toDto( message ) );
        }

        return list1;
    }
}
