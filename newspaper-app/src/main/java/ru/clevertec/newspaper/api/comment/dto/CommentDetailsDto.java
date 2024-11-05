package ru.clevertec.newspaper.api.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CommentDetailsDto(
    @Schema(description = "Comment Id", example = "1")
    Long id,
    @Schema(description = "Date", example = "2024.12.12 12:12")
    LocalDateTime localDateTime,
    @Schema(description = "Comment text", example = "Example")
    String text,
    @Schema(description = "Comment user", example = "Username")
    String username

) {
}
