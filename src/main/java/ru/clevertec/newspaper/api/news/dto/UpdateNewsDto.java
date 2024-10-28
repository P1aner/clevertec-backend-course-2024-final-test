package ru.clevertec.newspaper.api.news.dto;

import java.time.LocalDateTime;

public record UpdateNewsDto(

    LocalDateTime localDateTime,

    String title,

    String text

) {
}
