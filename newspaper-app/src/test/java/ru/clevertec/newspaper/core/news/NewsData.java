package ru.clevertec.newspaper.core.news;

import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;
import ru.clevertec.newspaper.core.comment.CommentData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsData {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2024, 12, 12, 12, 12);

    public static News fullNewsWithoutComments() {
        return News.builder()
            .id(1L)
            .title("Title")
            .username("user")
            .text("Text")
            .localDateTime(LOCAL_DATE_TIME)
            .commentList(new ArrayList<>())
            .build();
    }

    public static News fullNewsWithoutCommentsAndId() {
        return News.builder()
            .title("Title")
            .username("user")
            .text("Text")
            .localDateTime(LOCAL_DATE_TIME)
            .commentList(new ArrayList<>())
            .build();
    }

    public static NewNewsDto newNewsDto() {
        return NewNewsDto.newBuilder()
            .setTimestamp("2024-12-12T12:12:00")
            .setUsername("user")
            .setTitle("Title")
            .setText("Text")
            .build();
    }

    public static NewsDetailsDto newsDetailsDto() {
        return NewsDetailsDto.newBuilder()
            .setId(1L)
            .setTimestamp("2024-12-12T12:12:00")
            .setUsername("user")
            .setTitle("Title")
            .setText("Text")
            .addAllCommentList(List.of(CommentData.commentDetailsDto()))
            .build();
    }

    public static UpdateNewsDto updateNewsDto() {
        return UpdateNewsDto.newBuilder()
            .setTimestamp("2024-12-12T12:12:00")
            .setUsername("user")
            .setTitle("Title")
            .setText("Text")
            .build();
    }

    public static NewsTitleDto newsTitleDto() {
        return NewsTitleDto.newBuilder()
            .setId(1L)
            .setTitle("Title")
            .build();
    }

    public static NewsDetailsDto newsDetailsDtoWithoutComments() {
        return NewsDetailsDto.newBuilder()
            .setId(1L)
            .setTimestamp("2024-12-12T12:12:00")
            .setUsername("user")
            .setTitle("Title")
            .setText("Text")
            .addAllCommentList(List.of())
            .build();
    }
}
