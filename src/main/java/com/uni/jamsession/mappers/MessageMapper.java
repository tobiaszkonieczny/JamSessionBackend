package com.uni.jamsession.mappers;

import com.uni.jamsession.dtos.MessageDto;
import com.uni.jamsession.dtos.UserDto;
import com.uni.jamsession.model.*;
import com.uni.jamsession.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class MessageMapper {

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserMapper userMapper;

    @Mapping(source = "content", target = "message")
    @Mapping(source = "image", target = "imageUrl", qualifiedByName = "mapImageToUrl")
    @Mapping(source = "user", target = "author", qualifiedByName = "mapUserIdToUserDto")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "reactionCount", expression = "java(Long.valueOf(message.getReactions().size()))")
    @Mapping(target = "reactionSummary", expression = "java(mapReactions(message.getReactions()))")
    public abstract MessageDto toDto(Message message);

    protected Map<ReactionType, Long> mapReactions(Set<Reaction> reactions) {
        if (reactions == null) return Map.of();
        return reactions.stream()
                .collect(Collectors.groupingBy(Reaction::getType, Collectors.counting()));
    }



    @Named("mapImageToUrl")
    protected String mapImageToUrl(ImageData image) {
        if (image == null) return null;
        return "images/" + image.getId();
    }
    @Named("mapUserIdToUserDto")
    protected UserDto mapUserIdToUserDto(User user) {
        if (user == null) return null;

        return userMapper.userToUserDto(user);
    }
}