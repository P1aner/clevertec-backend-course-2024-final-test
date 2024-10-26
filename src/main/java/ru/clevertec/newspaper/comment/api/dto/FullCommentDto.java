package ru.clevertec.newspaper.comment.api.dto;

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
public class FullCommentDto {
    private Long id;
    private LocalDateTime dateTime;
    private String text;
    private String username;
}
