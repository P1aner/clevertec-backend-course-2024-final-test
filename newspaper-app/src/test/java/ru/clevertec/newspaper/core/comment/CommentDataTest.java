package ru.clevertec.newspaper.core.comment;

import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.core.news.News;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDataTest {

    public static final String NEW_COMMENT = """
            {
        "localDateTime":"2024-12-12T12:12:00",
        "text":"Text",
        "username":"username"
        }""";

    public static final String NEW_COMMENT_WITH_ID = """
        {
        "id": 1,
        "localDateTime":"2024-12-12T12:12:00",
        "text":"Text",
        "username":"username"
        }
        """;
    public static final String UPDATE_COMMENT = """
        {
        "localDateTime":"2024-12-12T12:12:00",
        "text":"Text",
        "username":"username"
        }
        """;
    public static final String COMMENTS_LIST = """
        {
          "id": 1,
          "localDateTime": "2024-12-12T12:12:00",
          "title": "Title",
          "text": "Text",
          "commentList": [
            {
              "id": 1,
              "localDateTime": "2024-12-12T12:12:00",
              "text": "Text",
              "username": "username"
            }
          ]
        }
        """;

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
        return new NewCommentDto(LocalDateTime.of(2024, 12, 12, 12, 12), "Text of fullComment", "username");
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
        return new CommentDetailsDto(1L, LocalDateTime.of(2024, 12, 12, 12, 12), "Text", "username");
    }

    public static UpdateCommentDto updateCommentDto() {
        return new UpdateCommentDto(LocalDateTime.of(2024, 12, 12, 12, 12), "Text", "username");
    }

    public static NewsDetailsDto newsDetailsDto() {
        return new NewsDetailsDto(1L, LocalDateTime.of(2024, 12, 12, 12, 12), "user", "Title", "Text", List.of(commentDetailsDto()));
    }

}
