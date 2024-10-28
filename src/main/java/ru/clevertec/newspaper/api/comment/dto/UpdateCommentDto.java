package ru.clevertec.newspaper.api.comment.dto;

import java.time.LocalDateTime;

public record UpdateCommentDto(

    LocalDateTime localDateTime,

    String text,

    String username

) {
}
