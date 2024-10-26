package ru.clevertec.newspaper.comment.core;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.newspaper.comment.api.dto.EditCommentDto;
import ru.clevertec.newspaper.comment.api.dto.FullCommentDto;
import ru.clevertec.newspaper.comment.api.dto.NewCommentDto;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {

    FullCommentDto toCommentDto(Comment comment);

    Comment toComment(NewCommentDto commentDto);

    void updateCommentFromDto(EditCommentDto editCommentDto, @MappingTarget Comment comment);

    List<FullCommentDto> toCommentListDto(List<Comment> all);
}
