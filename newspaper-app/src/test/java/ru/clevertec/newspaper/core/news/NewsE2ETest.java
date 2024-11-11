package ru.clevertec.newspaper.core.news;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.clevertec.newspaper.PostgresContainerConfiguration;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDtoList;
import ru.clevertec.newspaper.core.secure.SecureData;
import ru.clevertec.newspaper.core.secure.SecuredWireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
@Sql(scripts = "/db/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class NewsE2ETest extends PostgresContainerConfiguration {

    @RegisterExtension
    static WireMockExtension wm = SecuredWireMock.wm;

    @BeforeEach
    public void configureWireMock() {
        SecuredWireMock.configureWireMock();
    }

    @LocalServerPort
    private int port;

    private final RestClient restClient = RestClient.builder()
        .messageConverters(converters -> converters.add(new ProtobufHttpMessageConverter()))
        .defaultHeader("Accept", "application/x-protobuf")
        .defaultHeader("Content-Type", "application/x-protobuf")
        .build();

    @Test
    void positiveCasePostNewsRequest() {
        String uri = "http://localhost:%s/api/news".formatted(port);
        NewsDetailsDto newsDetailsDto1 = NewsDetailsDto.newBuilder()
            .setId(2L)
            .setTimestamp("2024-12-12T12:12:00")
            .setUsername("user")
            .setTitle("Title")
            .setText("Text")
            .build();
        NewNewsDto newNewsDto = NewsData.newNewsDto();

        ResponseEntity<NewsDetailsDto> response = getEntity(restClient
            .post()
            .uri(uri)
            .body(newNewsDto)
            .header(SecureData.AUTHORIZATION, SecureData.BASIC)
            .retrieve());
        NewsDetailsDto newsDetailsDto = response.getBody();

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(newsDetailsDto1, newsDetailsDto);
    }

    @Test
    void positiveCaseGetNewsRequest() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);
        NewsDetailsDto newsDetailsDto1 = NewsData.newsDetailsDto();

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
        NewsDetailsDto newsDetailsDto1 = NewsData.newsDetailsDto();
        NewNewsDto newNewsDto = NewsData.newNewsDto();

        ResponseEntity<NewsDetailsDto> response = getEntity(restClient
            .patch()
            .uri(uri)
            .body(newNewsDto)
            .header(SecureData.AUTHORIZATION, SecureData.BASIC)
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
            .body(NewsData.newNewsDto())
            .header(SecureData.AUTHORIZATION, SecureData.BASIC)
            .retrieve()
            .toEntity(CommentDetailsDto.class);
    }

    @Test
    void deleteNews() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);

        ResponseEntity<NewsDetailsDto> response = getEntity(restClient
            .delete()
            .uri(uri)
            .header(SecureData.AUTHORIZATION, SecureData.BASIC)
            .retrieve());

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void afterDeleteNews() {
        String uri = "http://localhost:%s/api/news/1".formatted(port);

        ResponseEntity<NewsDetailsDto> entity = getEntity(restClient
            .delete()
            .uri(uri)
            .header(SecureData.AUTHORIZATION, SecureData.BASIC)
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
            .header(SecureData.AUTHORIZATION, SecureData.BASIC)
            .retrieve());
    }

    private @NotNull ResponseEntity<NewsDetailsDto> getEntity(RestClient.ResponseSpec restClient) {
        return restClient
            .toEntity(NewsDetailsDto.class);
    }

    @Test
    void findCommentByNews() {
        String uri = "http://localhost:%s/api/news".formatted(port);

        ResponseEntity<NewsTitleDtoList> entity = restClient
            .get()
            .uri(uri)
            .header(SecureData.AUTHORIZATION, SecureData.BASIC)
            .retrieve()
            .toEntity(NewsTitleDtoList.class);
        NewsTitleDtoList body = entity.getBody();

        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
        assert body != null;
        Assertions.assertEquals(1, body.getNewsListList().size());
        Assertions.assertEquals("Title", body.getNewsListList().getFirst().getTitle());
        Assertions.assertEquals(1L, body.getNewsListList().getFirst().getId());
    }
}
