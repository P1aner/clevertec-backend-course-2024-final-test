package ru.clevertec.newspaper.core.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.cache.Cache;
import ru.clevertec.exception.exception.ProblemUtil;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.core.comment.Comment;
import ru.clevertec.newspaper.core.comment.CommentMapper;
import ru.clevertec.newspaper.core.comment.CommentRepository;
import ru.clevertec.newspaper.core.news.News;
import ru.clevertec.newspaper.core.news.NewsMapper;
import ru.clevertec.newspaper.core.news.NewsRepository;

import java.util.List;

/**
 * Provides tools for working with news comments.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceBase implements CommentService {

    private static final String COMMENT = "Comment";
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final CommentMapper commentMapper;
    private final NewsMapper newsMapper;
    private final Cache<String, Object> cache;

    /**
     * Adds a comment to an existing news article.
     * If the news article is not found, it throws a ResourceNotFoundException.
     *
     * @param newsId        News id
     * @param newCommentDto New comment without id
     * @return New comment with id
     */
    @Override
    public CommentDetailsDto createComment(Long newsId, NewCommentDto newCommentDto) {
        Comment comment = commentMapper.toComment(newCommentDto);
        News news = newsRepository.findById(newsId)
            .orElseThrow(() -> ProblemUtil.resourceNotFound("News", newsId));
        comment.setNews(news);
        Comment save = commentRepository.save(comment);

        String key = cache.generateKey(Comment.class, newsId, save.getId());
        cache.put(key, save);

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
    @Override
    public CommentDetailsDto getComment(Long newsId, Long commentId) {
        String key = cache.generateKey(Comment.class, commentId, newsId);
        return commentMapper.toCommentDto(cache.get(key)
            .map(m -> (Comment) m)
            .orElseGet(() -> {
                Comment comment = commentRepository.findByNews_IdAndId(newsId, commentId)
                    .orElseThrow(() -> ProblemUtil.resourceNotFound(COMMENT, commentId));
                cache.put(key, comment);
                return comment;
            }));
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
    @Override
    public CommentDetailsDto updateComment(Long newsId, Long commentId, UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findByNews_IdAndId(newsId, commentId)
            .orElseThrow(() -> ProblemUtil.resourceNotFound(COMMENT, commentId));
        commentMapper.updateCommentFromDto(updateCommentDto, comment);
        Comment save = commentRepository.save(comment);

        String key = cache.generateKey(Comment.class, commentId, newsId);
        cache.put(key, save);

        return commentMapper.toCommentDto(save);
    }

    /**
     * Deletes the comment if it exists.
     * If the news comment is not found, it throws a ResourceNotFoundException.
     *
     * @param newsId    News id
     * @param commentId Comment id
     */
    @Override
    public void deleteComment(Long newsId, Long commentId) {
        News news = newsRepository.findById(newsId)
            .orElseThrow(() -> ProblemUtil.resourceNotFound("News", newsId));
        if (!commentRepository.existsByNews_IdAndId(newsId, commentId)) {
            throw ProblemUtil.resourceNotFound(COMMENT, commentId);
        }
        news.getCommentList().removeIf(i -> i.getId().equals(commentId));

        News save = newsRepository.save(news);
        String newsKey = cache.generateKey(News.class, newsId);
        cache.put(newsKey, save);

        commentRepository.deleteById(commentId);
        String key = cache.generateKey(Comment.class, commentId, newsId);
        cache.delete(key);
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
    @Override
    public NewsDetailsDto findCommentByNews(Long newsId, String query, Pageable pageable) {
        News news = newsRepository.findById(newsId)
            .orElseThrow(() -> ProblemUtil.resourceNotFound("News", newsId));
        Page<Comment> comments;
        if (StringUtils.isEmpty(query)) {
            log.debug("Query is empty");
            comments = commentRepository.findByNews_Id(newsId, pageable);
        } else {
            log.debug("Query is not empty and contains: {}", query);
            comments = commentRepository.findByTextContainsIgnoreCaseAndNews_Id(query, newsId, pageable);
        }
        List<Comment> content = comments.getContent();
        news.setCommentList(content);
        return newsMapper.toNewsDto(news);
    }

}
