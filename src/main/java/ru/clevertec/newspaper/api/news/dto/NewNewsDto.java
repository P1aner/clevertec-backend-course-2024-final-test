package ru.clevertec.newspaper.api.news.dto;

import java.time.LocalDateTime;

public record NewNewsDto(

    LocalDateTime localDateTime,

    String title,

    String text

) {
}
