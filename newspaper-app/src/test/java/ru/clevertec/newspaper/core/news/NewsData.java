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

    public static final String NEW_NEWS_CONTENT = """
        {
        "localDateTime":"2024-12-12T12:12:00",
        "username":"user",
        "title":"Title",
        "text":"Text"
        }
        """;

    public static final String FULL_NEWS = """
        {
        "id":1,
        "localDateTime":"2024-12-12T12:12:00",
        "username":"user",
        "title":"Title",
        "text":"Text",
        "commentList":[]
        }
        """;
    public static final String TITLE_NEWS_LIST = """
        [{
          "id":1,
          "title":"Title"
          }]
        """;


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
        return new NewNewsDto(LOCAL_DATE_TIME, "user", "Title", "Text");
    }

    public static NewsDetailsDto newsDetailsDto() {
        return new NewsDetailsDto(1L, LOCAL_DATE_TIME, "user", "Title", "Text", List.of(CommentData.commentDetailsDto()));
    }

    public static UpdateNewsDto updateNewsDto() {
        return new UpdateNewsDto(LOCAL_DATE_TIME, "user", "Title", "Text");
    }

    public static NewsTitleDto newsTitleDto() {
        return new NewsTitleDto(1L, "Title");
    }

    public static NewsDetailsDto newsDetailsDtoWithoutComments() {
        return new NewsDetailsDto(1L, LOCAL_DATE_TIME, "user", "Title", "Text", List.of());

    }
}
