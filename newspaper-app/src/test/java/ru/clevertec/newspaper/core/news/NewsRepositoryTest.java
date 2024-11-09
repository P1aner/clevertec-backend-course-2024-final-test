package ru.clevertec.newspaper.core.news;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.newspaper.PostgresContainerConfiguration;
import ru.clevertec.newspaper.core.comment.Comment;
import ru.clevertec.newspaper.core.comment.CommentData;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
@Sql(scripts = "/db/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class NewsRepositoryTest extends PostgresContainerConfiguration {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    void findById() {
        Optional<News> byId = newsRepository.findById(1L);

        Assertions.assertTrue(byId.isPresent());
        Assertions.assertEquals("Text", byId.get().getText());
        Assertions.assertEquals("Title", byId.get().getTitle());
        Assertions.assertEquals(1, byId.get().getCommentList().size());
    }

    @Test
    void findAll() {
        newsRepository.save(NewsData.fullNewsWithoutCommentsAndId());
        List<News> all = newsRepository.findAll();
        Assertions.assertEquals(2, all.size());
    }

    @Test
    void saveNews() {
        News save = newsRepository.save(NewsData.fullNewsWithoutCommentsAndId());
        Assertions.assertEquals(2L, save.getId());
    }

    @Test
    void saveNewsAndComment() {
        News entity = NewsData.fullNewsWithoutCommentsAndId();
        Comment comment = CommentData.fullCommentWithoutNewsAndId();

        entity.getCommentList().add(comment);
        comment.setNews(entity);
        News save = newsRepository.save(entity);
        Comment first = save.getCommentList().getFirst();
        Long newsFormCommentId = first.getNews().getId();
        Long newsId = save.getId();

        Assertions.assertEquals(newsFormCommentId, newsId);
    }
}