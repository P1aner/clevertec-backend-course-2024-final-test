package ru.clevertec.newspaper.core.comment;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {

    CommentDetailsDto toCommentDto(Comment comment);

    Comment toComment(NewCommentDto commentDto);

    void updateCommentFromDto(UpdateCommentDto updateCommentDto, @MappingTarget Comment comment);

    List<CommentDetailsDto> toCommentListDto(List<Comment> all);
}
