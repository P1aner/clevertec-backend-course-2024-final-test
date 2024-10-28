package ru.clevertec.newspaper.api.news.dto;

import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;

import java.time.LocalDateTime;
import java.util.List;

public record NewsDetailsDto(

    Long id,

    LocalDateTime localDateTime,

    String title,

    String text,

    List<CommentDetailsDto> commentList

) {
}
