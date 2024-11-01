package ru.clevertec.newspaper.core.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.newspaper.PostgresContainerConfiguration;
import ru.clevertec.newspaper.core.news.NewsRepository;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = PostgresContainerConfiguration.class)
@Sql(scripts = "/db/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/db/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CommentRepositoryTest extends PostgresContainerConfiguration {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Test
    void findByNews_Id() {
        Page<Comment> byNewsId = commentRepository.findByNews_Id(1L, PageRequest.of(0, 10));

        Assertions.assertEquals(byNewsId.getContent().size(), 1);
    }

    @Test
    void findByNews_IdAndId() {
        Optional<Comment> byNewsIdAndId = commentRepository.findByNews_IdAndId(1L, 1L);

        Assertions.assertTrue(byNewsIdAndId.isPresent());
    }

    @Test
    void findByTextContainsIgnoreCaseAndNews_Id() {
        int size = commentRepository.findByTextContainsIgnoreCaseAndNews_Id("Text", 1L, PageRequest.of(0, 10)).getContent().size();

        Assertions.assertEquals(size, 1);
    }

    @Test
    void failFindByTextContainsIgnoreCaseAndNews_Id() {
        int size = commentRepository.findByTextContainsIgnoreCaseAndNews_Id("Not text", 1L, PageRequest.of(0, 10)).getContent().size();

        Assertions.assertEquals(size, 0);
    }

    @Test
    void existsByNews_IdAndId() {
        boolean b = commentRepository.existsByNews_IdAndId(1L, 1L);

        Assertions.assertTrue(b);
    }
}