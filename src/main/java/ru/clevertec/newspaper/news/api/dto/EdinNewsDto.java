package ru.clevertec.newspaper.news.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EdinNewsDto {
    private LocalDateTime dateTime;
    private String title;
    private String text;
}
