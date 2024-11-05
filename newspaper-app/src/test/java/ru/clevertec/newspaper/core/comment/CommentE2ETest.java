package ru.clevertec.newspaper.core.comment;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.clevertec.newspaper.PostgresContainerConfiguration;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
@Sql(scripts = "/db/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CommentE2ETest extends PostgresContainerConfiguration {

    @LocalServerPort
    private int port;

    private final RestClient restClient = RestClient.create();

    @Test
    void positiveCasePostCommentRequest() {
        String uri = "http://localhost:%s/api/news/1/comments".formatted(port);
        CommentDetailsDto commentDetailsDto1 = new CommentDetailsDto(2L, LocalDateTime.of(2024, 12, 12, 12, 12), "Text", "username");

        ResponseEntity<CommentDetailsDto> response = restClient
            .post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(CommentDataTest.NEW_COMMENT)
            .retrieve()
            .toEntity(CommentDetailsDto.class);
        CommentDetailsDto commentDetailsDto = response.getBody();

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(commentDetailsDto, commentDetailsDto1);
    }


    @Test
    void positiveCaseGetCommentRequest() {
        String uri = "http://localhost:%s/api/news/1/comments/1".formatted(port);
        CommentDetailsDto commentDetailsDto1 = CommentDataTest.commentDetailsDto();

        ResponseEntity<CommentDetailsDto> response = restClient
            .get()
            .uri(uri)
            .retrieve()
            .toEntity(CommentDetailsDto.class);
        CommentDetailsDto commentDetailsDto = response.getBody();

        Assertions.assertEquals(commentDetailsDto, commentDetailsDto1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void negativeCaseGetCommentRequest() {
        String uri = "http://localhost:%s/api/news/2/comments/1".formatted(port);

        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, getThrowingRunnable(restClient
            .get()
            .uri(uri)
            .retrieve()));

        Assertions.assertTrue(httpClientErrorException.getMessage().contains("Comment id: 1 not found."));
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("404"));
    }

    @Test
    void patchCommentRequest() {
        String uri = "http://localhost:%s/api/news/1/comments/1".formatted(port);
        CommentDetailsDto commentDetailsDto1 = CommentDataTest.commentDetailsDto();

        ResponseEntity<CommentDetailsDto> response = restClient
            .patch()
            .uri(uri)
            .body(CommentDataTest.UPDATE_COMMENT)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(CommentDetailsDto.class);
        CommentDetailsDto commentDetailsDto = response.getBody();

        Assertions.assertEquals(commentDetailsDto, commentDetailsDto1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void negativeCasePatchCommentRequest() {
        String uri = "http://localhost:%s/api/news/2/comments/1".formatted(port);

        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, getThrowingRunnable(restClient
            .patch()
            .uri(uri)
            .body(CommentDataTest.UPDATE_COMMENT)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()));

        Assertions.assertTrue(httpClientErrorException.getMessage().contains("Comment id: 1 not found."));
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("404"));
    }

    @Test
    void deleteComment() {
        String uri = "http://localhost:%s/api/news/1/comments/1".formatted(port);

        ResponseEntity<CommentDetailsDto> response = restClient
            .delete()
            .uri(uri)
            .retrieve()
            .toEntity(CommentDetailsDto.class);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void afterDeleteComment() {
        String uri = "http://localhost:%s/api/news/1/comments/1".formatted(port);

        ResponseEntity<CommentDetailsDto> entity = restClient
            .delete()
            .uri(uri)
            .retrieve()
            .toEntity(CommentDetailsDto.class);
        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, getThrowingRunnable(restClient
            .get()
            .uri(uri)
            .retrieve()));

        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("Comment id: 1 not found."));
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("404"));
    }

    private @NotNull ThrowingRunnable getThrowingRunnable(RestClient.ResponseSpec restClient) {
        return () -> restClient
            .toEntity(CommentDetailsDto.class);
    }

    @Test
    void findCommentByNews() {
        NewsDetailsDto newsDetailsDto = CommentDataTest.newsDetailsDto();
        String uri = "http://localhost:%s/api/news/1/comments".formatted(port);

        ResponseEntity<NewsDetailsDto> entity = restClient
            .get()
            .uri(uri)
            .retrieve()
            .toEntity(NewsDetailsDto.class);
        NewsDetailsDto body = entity.getBody();

        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assertions.assertEquals(body, newsDetailsDto);
    }
}