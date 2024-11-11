package ru.clevertec.newspaper.core.comment;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Mapping(source = "localDateTime", target = "timestamp", qualifiedByName = "localDateTimeToTimestamp")
    CommentDetailsDto toCommentDto(Comment comment);

    @Mapping(source = "timestamp", target = "localDateTime", qualifiedByName = "timestampToLocalDateTime")
    Comment toComment(NewCommentDto commentDto);

    @Mapping(source = "timestamp", target = "localDateTime", qualifiedByName = "timestampToLocalDateTime")
    void updateCommentFromDto(UpdateCommentDto updateCommentDto, @MappingTarget Comment comment);

    @Named("localDateTimeToTimestamp")
    default String localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.format(formatter) : null;
    }

    @Named("timestampToLocalDateTime")
    default LocalDateTime timestampToLocalDateTime(String dateTime) {
        return dateTime != null ? LocalDateTime.parse(dateTime, formatter) : null;
    }

}
