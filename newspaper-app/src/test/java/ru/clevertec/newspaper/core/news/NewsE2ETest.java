package ru.clevertec.newspaper.core.news;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
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
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
@Sql(scripts = "/db/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class NewsE2ETest extends PostgresContainerConfiguration {

    @LocalServerPort
    private int port;

    private final RestClient restClient = RestClient.create();

    @Test
    void positiveCasePostNewsRequest() {
        String uri = "http://localhost:%s/api/news".formatted(port);
        NewsDetailsDto newsDetailsDto1 = new NewsDetailsDto(2L, LocalDateTime.of(2024, 12, 12, 12, 12), "Title", "Text", null);

        ResponseEntity<NewsDetailsDto> response = getEntity(restClient
            .post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(NewsDataTest.NEW_NEWS_CONTENT)
            .retrieve());
        NewsDetailsDto newsDetailsDto = response.getBody();

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(newsDetailsDto, newsDetailsDto1);
    }

    @Test
    void positiveCaseGetNewsRequest() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);
        NewsDetailsDto newsDetailsDto1 = NewsDataTest.newsDetailsDto();

        ResponseEntity<NewsDetailsDto> response = getNewsDetailsDtoResponseEntity(uri);
        NewsDetailsDto newsDetailsDto = response.getBody();

        Assertions.assertEquals(newsDetailsDto, newsDetailsDto1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void negativeCaseGetNewsRequest() {
        String uri = "http://localhost:%s/api/news/2".formatted(port);

        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, () -> getNewsDetailsDtoResponseEntity(uri));

        Assertions.assertTrue(httpClientErrorException.getMessage().contains("News id: 2 not found."));
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("404"));
    }

    @Test
    void patchNewsRequest() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);
        NewsDetailsDto newsDetailsDto1 = NewsDataTest.newsDetailsDto();

        ResponseEntity<NewsDetailsDto> response = getEntity(restClient
            .patch()
            .uri(uri)
            .body(NewsDataTest.NEW_NEWS_CONTENT)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve());
        NewsDetailsDto newsDetailsDto = response.getBody();

        Assertions.assertEquals(newsDetailsDto, newsDetailsDto1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void negativeCasePatchNewsRequest() {
        String uri = "http://localhost:%s/api/news/2".formatted(port);

        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, () -> getEntity(uri));

        Assertions.assertTrue(httpClientErrorException.getMessage().contains("News id: 2 not found."));
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("404"));
    }

    private void getEntity(String uri) {
        restClient
            .patch()
            .uri(uri)
            .body(NewsDataTest.NEW_NEWS_CONTENT)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(CommentDetailsDto.class);
    }

    @Test
    void deleteNews() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);

        ResponseEntity<NewsDetailsDto> response = getEntity(restClient
            .delete()
            .uri(uri)
            .retrieve());

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void afterDeleteNews() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);

        ResponseEntity<NewsDetailsDto> entity = getEntity(restClient
            .delete()
            .uri(uri)
            .retrieve());
        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, () -> getNewsDetailsDtoResponseEntity(uri));

        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("News id: 1 not found."));
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("404"));
    }

    private @NotNull ResponseEntity<NewsDetailsDto> getNewsDetailsDtoResponseEntity(String uri) {
        return getEntity(restClient
            .get()
            .uri(uri)
            .retrieve());
    }

    private @NotNull ResponseEntity<NewsDetailsDto> getEntity(RestClient.ResponseSpec restClient) {
        return restClient
            .toEntity(NewsDetailsDto.class);
    }

    @Test
    void findCommentByNews() {
        String uri = "http://localhost:%s/api/news".formatted(port);

        ResponseEntity<List<NewsTitleDto>> entity = restClient
            .get()
            .uri(uri)
            .retrieve()
            .toEntity(new ParameterizedTypeReference<>() {
            });
        List<NewsTitleDto> body = entity.getBody();

        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        assert body != null;
        Assertions.assertEquals(1, body.size());
        Assertions.assertEquals("Title", body.getFirst().title());
        Assertions.assertEquals(1L, body.getFirst().id());
    }
}