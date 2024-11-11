package ru.clevertec.newspaper.core.comment;

import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.core.news.News;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentData {

    public static Comment fullComment() {
        return Comment.builder()
            .id(1L)
            .username("username")
            .text("Text of fullComment")
            .localDateTime(LocalDateTime.of(2024, 12, 12, 12, 12))
            .news(fullNewsWithoutComments())
            .build();
    }

    public static Comment fullCommentWithoutNews() {
        return Comment.builder()
            .id(1L)
            .username("username")
            .text("Text of fullComment")
            .localDateTime(LocalDateTime.of(2024, 12, 12, 12, 12))
            .build();
    }

    public static Comment fullCommentWithoutNewsAndId() {
        return Comment.builder()
            .username("username")
            .text("Text of fullComment")
            .localDateTime(LocalDateTime.of(2024, 12, 12, 12, 12))
            .build();
    }

    public static NewCommentDto newCommentDto() {
        return NewCommentDto.newBuilder()
            .setTimestamp("2024-12-12T12:12:00")
            .setText("Text")
            .setUsername("username")
            .build();
    }

    public static News fullNewsWithoutComments() {
        return News.builder()
            .id(1L)
            .title("Title")
            .text("Text")
            .localDateTime(LocalDateTime.of(2024, 12, 12, 12, 12))
            .commentList(new ArrayList<>())
            .build();
    }

    public static CommentDetailsDto commentDetailsDto() {
        return CommentDetailsDto.newBuilder()
            .setId(1L)
            .setTimestamp("2024-12-12T12:12:00")
            .setText("Text")
            .setUsername("username")
            .build();
    }

    public static UpdateCommentDto updateCommentDto() {
        return UpdateCommentDto.newBuilder()
            .setTimestamp("2024-12-12T12:12:00")
            .setText("Text")
            .setUsername("username")
            .build();
    }

    public static NewsDetailsDto newsDetailsDto() {
        return NewsDetailsDto.newBuilder()
            .setId(1L)
            .setTimestamp("2024-12-12T12:12:00")
            .setUsername("user")
            .setTitle("Title")
            .setText("Text")
            .addAllCommentList(List.of(commentDetailsDto()))
            .build();
    }

}
