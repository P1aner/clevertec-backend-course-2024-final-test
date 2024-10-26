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
public class EditCommentDto {
    private LocalDateTime localDateTime;
    private String text;
    private String username;
}
