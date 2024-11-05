package ru.clevertec.newspaper.core.news;

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
public class NewsE2ETest extends PostgresContainerConfiguration {

    @LocalServerPort
    private int port;

    private final RestClient restClient = RestClient.create();

    @Test
    void positiveCasePostNewsRequest() {
        String uri = "http://localhost:%s/api/news".formatted(port);
        NewsDetailsDto newsDetailsDto1 = new NewsDetailsDto(2L, LocalDateTime.of(2024, 12, 12, 12, 12), "Title", "Text", null);

        ResponseEntity<NewsDetailsDto> response = restClient
            .post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(NewsDataTest.NEW_NEWS_CONTENT)
            .retrieve()
            .toEntity(NewsDetailsDto.class);
        NewsDetailsDto newsDetailsDto = response.getBody();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertEquals(newsDetailsDto, newsDetailsDto1);
    }


    @Test
    void positiveCaseGetNewsRequest() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);
        NewsDetailsDto newsDetailsDto1 = NewsDataTest.newsDetailsDto();

        ResponseEntity<NewsDetailsDto> response = restClient
            .get()
            .uri(uri)
            .retrieve()
            .toEntity(NewsDetailsDto.class);
        NewsDetailsDto newsDetailsDto = response.getBody();

        Assertions.assertEquals(newsDetailsDto, newsDetailsDto1);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void negativeCaseGetNewsRequest() {
        String uri = "http://localhost:%s/api/news/2".formatted(port);

        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, () -> restClient
            .get()
            .uri(uri)
            .retrieve()
            .toEntity(NewsDetailsDto.class));

        Assertions.assertTrue(httpClientErrorException.getMessage().contains("News id: 2 not found."));
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("404"));
    }

    @Test
    void patchNewsRequest() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);
        NewsDetailsDto newsDetailsDto1 = NewsDataTest.newsDetailsDto();

        ResponseEntity<NewsDetailsDto> response = restClient
            .patch()
            .uri(uri)
            .body(NewsDataTest.NEW_NEWS_CONTENT)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(NewsDetailsDto.class);
        NewsDetailsDto newsDetailsDto = response.getBody();

        Assertions.assertEquals(newsDetailsDto, newsDetailsDto1);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void negativeCasePatchNewsRequest() {
        String uri = "http://localhost:%s/api/news/2".formatted(port);

        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, () -> restClient
            .patch()
            .uri(uri)
            .body(NewsDataTest.NEW_NEWS_CONTENT)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(CommentDetailsDto.class));

        Assertions.assertTrue(httpClientErrorException.getMessage().contains("News id: 2 not found."));
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("404"));
    }

    @Test
    void deleteNews() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);

        ResponseEntity<NewsDetailsDto> response = restClient
            .delete()
            .uri(uri)
            .retrieve()
            .toEntity(NewsDetailsDto.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    void afterDeleteNews() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);

        ResponseEntity<NewsDetailsDto> entity = restClient
            .delete()
            .uri(uri)
            .retrieve()
            .toEntity(NewsDetailsDto.class);
        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, () -> restClient
            .get()
            .uri(uri)
            .retrieve()
            .toEntity(NewsDetailsDto.class));

        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.NO_CONTENT);
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("News id: 1 not found."));
        Assertions.assertTrue(httpClientErrorException.getMessage().contains("404"));
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

        Assertions.assertEquals(entity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(body.size(), 1);
        Assertions.assertEquals(body.getFirst().title(), "Title");
        Assertions.assertEquals(body.getFirst().id(), 1L);
    }
}