package ru.clevertec.newspaper.api.comment.dto;

import java.time.LocalDateTime;

public record CommentDetailsDto(

    Long id,

    LocalDateTime localDateTime,

    String text,

    String username

) {
}
