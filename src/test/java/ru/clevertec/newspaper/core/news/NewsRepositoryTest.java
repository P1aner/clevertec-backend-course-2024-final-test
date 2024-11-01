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
import ru.clevertec.newspaper.core.comment.CommentDataTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
@Sql(scripts = "/db/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class NewsRepositoryTest extends PostgresContainerConfiguration {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    void findById() {
        Optional<News> byId = newsRepository.findById(1L);

        Assertions.assertTrue(byId.isPresent());
        Assertions.assertEquals(byId.get().getText(), "Text");
        Assertions.assertEquals(byId.get().getTitle(), "Title");
        Assertions.assertEquals(byId.get().getCommentList().size(), 1);
    }

    @Test
    void findAll() {
        News save = newsRepository.save(NewsDataTest.fullNewsWithoutCommentsAndId());
        List<News> all = newsRepository.findAll();
        Assertions.assertEquals(all.size(), 2);
    }

    @Test
    void saveNews() {
        News save = newsRepository.save(NewsDataTest.fullNewsWithoutCommentsAndId());
        Assertions.assertEquals(save.getId(), 2L);
    }

    @Test
    void saveNewsAndComment() {
        News entity = NewsDataTest.fullNewsWithoutCommentsAndId();
        Comment comment = CommentDataTest.fullCommentWithoutNewsAndId();

        entity.getCommentList().add(comment);
        comment.setNews(entity);
        News save = newsRepository.save(entity);
        Comment first = save.getCommentList().getFirst();
        Long newsFormCommentId = first.getNews().getId();
        Long newsId = save.getId();

        Assertions.assertEquals(newsFormCommentId, newsId);
    }
}