package ru.clevertec.newspaper.core.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.core.news.News;
import ru.clevertec.newspaper.core.news.NewsMapper;
import ru.clevertec.newspaper.core.news.NewsRepository;
import ru.clevertec.newspaper.exception.ProblemUtil;

import java.util.List;

/**
 * Provides tools for working with news comments.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final CommentMapper commentMapper;
    private final NewsMapper newsMapper;

    /**
     * Adds a comment to an existing news article.
     * If the news article is not found, it throws a ResourceNotFoundException.
     *
     * @param newsId        News id
     * @param newCommentDto New comment without id
     * @return New comment with id
     */
    public CommentDetailsDto createComment(Long newsId, NewCommentDto newCommentDto) {
        Comment comment = commentMapper.toComment(newCommentDto);
        News news = newsRepository.findById(newsId)
            .orElseThrow(() -> ProblemUtil.newsNotFound(newsId));
        comment.setNews(news);
        Comment save = commentRepository.save(comment);
        return commentMapper.toCommentDto(save);
    }

    /**
     * Displays a specific news comment.
     * If the news comment is not found, it throws a ResourceNotFoundException.
     *
     * @param newsId    News id
     * @param commentId Comment id
     * @return Comment with details
     */
    public CommentDetailsDto getComment(Long newsId, Long commentId) {
        Comment comment = commentRepository.findByNews_IdAndId(newsId, commentId)
            .orElseThrow(() -> ProblemUtil.commentNotFound(commentId));
        return commentMapper.toCommentDto(comment);
    }

    /**
     * Updates comment fields without changing the ID.
     * If the news comment is not found, it throws a ResourceNotFoundException.
     *
     * @param newsId           News id
     * @param commentId        Comment id
     * @param updateCommentDto Updated comment without id
     * @return Comment with details
     */
    public CommentDetailsDto updateComment(Long newsId, Long commentId, UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findByNews_IdAndId(newsId, commentId)
            .orElseThrow(() -> ProblemUtil.commentNotFound(commentId));
        commentMapper.updateCommentFromDto(updateCommentDto, comment);
        Comment save = commentRepository.save(comment);
        return commentMapper.toCommentDto(save);
    }

    /**
     * Deletes the comment if it exists.
     * If the news comment is not found, it throws a ResourceNotFoundException.
     *
     * @param newsId    News id
     * @param commentId Comment id
     */
    public void deleteComment(Long newsId, Long commentId) {
        if (commentRepository.existsByNews_IdAndId(newsId, commentId)) {
            throw ProblemUtil.commentNotFound(commentId);
        }
        commentRepository.deleteByNews_IdAndId(newsId, commentId);
    }

    /**
     * Searches for comments containing the search text and belonging to the same news article.
     * If no search text is provided, displays all comments.
     * If the news comment is not found, it throws a ResourceNotFoundException.
     *
     * @param newsId   News id
     * @param query    Search text.
     * @param pageable Settings of page.
     * @return The news article and its comment pages.
     */
    public NewsDetailsDto findCommentByNews(Long newsId, String query, Pageable pageable) {
        News news = newsRepository.findById(newsId)
            .orElseThrow(() -> ProblemUtil.newsNotFound(newsId));
        Page<Comment> comments;
        if (StringUtils.isEmpty(query)) {
            log.debug("Query is empty");
            comments = commentRepository.findByNews_Id(newsId, pageable);
        } else {
            log.debug("Query is not empty");
            comments = commentRepository.findByTextContainsIgnoreCaseAndNews_Id(query, newsId, pageable);
        }
        List<Comment> content = comments.getContent();
        news.setCommentList(content);
        return newsMapper.toNewsDto(news);

    }
}
