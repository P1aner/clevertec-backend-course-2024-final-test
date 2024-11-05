package ru.clevertec.newspaper.core.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.cache.Cache;
import ru.clevertec.exception.exception.ResourceNotFoundException;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.core.news.News;
import ru.clevertec.newspaper.core.news.NewsDataTest;
import ru.clevertec.newspaper.core.news.NewsMapper;
import ru.clevertec.newspaper.core.news.NewsRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private NewsMapper newsMapper;
    @Mock
    private Cache<String, Object> cache;

    @InjectMocks
    private CommentService commentService;


    @Test
    @DisplayName("Assigning news to a comment and saving it")
    void createComment() {
        NewCommentDto newCommentDto = CommentDataTest.newCommentDto();
        Comment fullCommentWithoutNewsAndId = CommentDataTest.fullCommentWithoutNewsAndId();
        News fullNewsWithoutComments = CommentDataTest.fullNewsWithoutComments();
        Comment fullComment = CommentDataTest.fullComment();
        CommentDetailsDto commentDetailsDto = CommentDataTest.commentDetailsDto();

        Mockito.when(commentMapper.toComment(newCommentDto)).thenReturn(fullCommentWithoutNewsAndId);
        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.of(fullNewsWithoutComments));
        Mockito.when(commentRepository.save(fullCommentWithoutNewsAndId)).thenReturn(fullComment);
        Mockito.when(commentMapper.toCommentDto(fullComment)).thenReturn(commentDetailsDto);

        CommentDetailsDto comment = commentService.createComment(1L, newCommentDto);

        Assertions.assertEquals(comment, commentDetailsDto);
        Assertions.assertEquals(comment.id(), 1L);
        Assertions.assertEquals(fullCommentWithoutNewsAndId.getNews().getId(), 1);

        Mockito.verify(commentRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(commentMapper, Mockito.times(1)).toComment(Mockito.any());
        Mockito.verify(commentMapper, Mockito.times(1)).toCommentDto(Mockito.any());
    }

    @Test
    @DisplayName("Fail to save comment")
    void createCommentFail() {
        NewCommentDto newCommentDto = CommentDataTest.newCommentDto();
        Comment fullCommentWithoutNewsAndId = CommentDataTest.fullCommentWithoutNewsAndId();

        Mockito.when(commentMapper.toComment(newCommentDto)).thenReturn(fullCommentWithoutNewsAndId);
        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentService.createComment(1L, newCommentDto));
    }

    @Test
    @DisplayName("Successful find comment")
    void getComment() {
        Comment fullComment = CommentDataTest.fullComment();
        CommentDetailsDto commentDetailsDto = CommentDataTest.commentDetailsDto();

        Mockito.when(commentRepository.findByNews_IdAndId(1L, 1L)).thenReturn(Optional.ofNullable(fullComment));
        Mockito.when(commentMapper.toCommentDto(fullComment)).thenReturn(commentDetailsDto);

        CommentDetailsDto comment = commentService.getComment(1L, 1L);

        Assertions.assertEquals(comment, commentDetailsDto);

        Mockito.verify(commentRepository, Mockito.times(1)).findByNews_IdAndId(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    @DisplayName("Fail find comment")
    void failGetComment() {
        Mockito.when(commentRepository.findByNews_IdAndId(1L, 1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentService.getComment(1L, 1L));
    }

    @Test
    @DisplayName("Successful comment update")
    void updateComment() {
        UpdateCommentDto updateCommentDto = CommentDataTest.updateCommentDto();
        Comment comment = CommentDataTest.fullCommentWithoutNews();
        CommentDetailsDto commentDetailsDto = CommentDataTest.commentDetailsDto();

        Mockito.when(commentRepository.findByNews_IdAndId(1L, 1L)).thenReturn(Optional.of(comment));
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
        Mockito.when(commentMapper.toCommentDto(comment)).thenReturn(commentDetailsDto);

        commentService.updateComment(1L, 1L, updateCommentDto);

        Mockito.verify(commentRepository, Mockito.times(1)).findByNews_IdAndId(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(commentRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(commentMapper, Mockito.times(1)).updateCommentFromDto(updateCommentDto, comment);
    }

    @Test
    @DisplayName("Fail comment update")
    void failUpdateComment() {
        UpdateCommentDto updateCommentDto = CommentDataTest.updateCommentDto();

        Mockito.when(commentRepository.findByNews_IdAndId(1L, 1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentService.updateComment(1L, 1L, updateCommentDto));
    }

    @Test
    @DisplayName("Successful comment delete")
    void deleteComment() {
        News fullNewsWithoutComments = NewsDataTest.fullNewsWithoutComments();

        Mockito.when(commentRepository.existsByNews_IdAndId(1L, 1L)).thenReturn(true);
        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.ofNullable(fullNewsWithoutComments));

        commentService.deleteComment(1L, 1L);

        Mockito.verify(commentRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Fail comment delete")
    void failDeleteComment() {
        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.of(NewsDataTest.fullNewsWithoutComments()));
        Mockito.when(commentRepository.existsByNews_IdAndId(1L, 1L)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentService.deleteComment(1L, 1L));
    }

    @Test
    @DisplayName("Find new's comments without query")
    void findCommentByNewsWithQuery() {
        Comment fullComment = CommentDataTest.fullComment();
        Page<Comment> comments = new PageImpl<>(List.of(fullComment));
        News fullNewsWithoutComments = CommentDataTest.fullNewsWithoutComments();
        Pageable pageable = Pageable.unpaged();
        String text = "query";

        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.of(fullNewsWithoutComments));
        Mockito.when(commentRepository.findByTextContainsIgnoreCaseAndNews_Id(text, 1L, pageable)).thenReturn(comments);

        commentService.findCommentByNews(1L, text, pageable);

        Mockito.verify(commentRepository, Mockito.times(1)).findByTextContainsIgnoreCaseAndNews_Id(text, 1L, pageable);
        Mockito.verify(commentRepository, Mockito.times(0)).findByNews_Id(1L, pageable);

        Assertions.assertEquals(fullNewsWithoutComments.getCommentList().getFirst(), fullComment);
    }

    @Test
    @DisplayName("Find new's comments without query")
    void findCommentByNewsWithoutQuery() {
        Comment fullComment = CommentDataTest.fullComment();
        Page<Comment> comments = new PageImpl<>(List.of(fullComment));
        News fullNewsWithoutComments = CommentDataTest.fullNewsWithoutComments();
        Pageable pageable = Pageable.unpaged();
        String text = "";

        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.of(fullNewsWithoutComments));
        Mockito.when(commentRepository.findByNews_Id(1L, pageable)).thenReturn(comments);

        commentService.findCommentByNews(1L, text, pageable);

        Mockito.verify(commentRepository, Mockito.times(0)).findByTextContainsIgnoreCaseAndNews_Id(text, 1L, pageable);
        Mockito.verify(commentRepository, Mockito.times(1)).findByNews_Id(1L, pageable);

        Assertions.assertEquals(fullNewsWithoutComments.getCommentList().getFirst(), fullComment);
    }

    @Test
    @DisplayName("Fail find new's comments")
    void failFindCommentByNews() {
        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> commentService.findCommentByNews(1L, "", Pageable.ofSize(1)));
    }
}