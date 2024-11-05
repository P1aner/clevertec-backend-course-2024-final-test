package ru.clevertec.newspaper.api.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;

import java.time.LocalDateTime;
import java.util.List;

public record NewsDetailsDto(
    @Schema(description = "News id", example = "1")
    Long id,
    @Schema(description = "Date", example = "2024.12.12 12:12")
    LocalDateTime localDateTime,
    @Schema(description = "News title", example = "Title of news")
    String title,
    @Schema(description = "News text", example = "Text of news")
    String text,
    @Schema(description = "Comments list")
    List<CommentDetailsDto> commentList

) {
}
