package ru.clevertec.newspaper.api.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record NewsTitleDto(
    @Schema(description = "News id", example = "1")
    Long id,
    @Schema(description = "News title", example = "Title of news")
    String title

) {
}
