package ru.clevertec.newspaper.api.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UpdateNewsDto(
    @Schema(description = "Date", example = "2024.12.12 12:12")
    LocalDateTime localDateTime,
    @Schema(description = "News title", example = "Title of news")
    String title,
    @Schema(description = "News text", example = "Text of news")
    String text

) {
}
