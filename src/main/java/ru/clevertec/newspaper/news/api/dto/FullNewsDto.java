package ru.clevertec.newspaper.news.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.newspaper.comment.api.dto.FullCommentDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullNewsDto {
    private Long id;
    private LocalDateTime localDateTime;
    private String title;
    private String text;
    private List<FullCommentDto> commentList;
}
