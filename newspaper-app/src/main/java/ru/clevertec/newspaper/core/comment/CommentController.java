package ru.clevertec.newspaper.core.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newspaper.api.comment.ApiComment;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;

/**
 * Provides tools for working with news comments
 */
@RestController
@RequiredArgsConstructor
public class CommentController implements ApiComment {
    private final CommentService commentService;

    /**
     * Adds a comment to an existing news article
     *
     * @param newsId        News id
     * @param newCommentDto New comment without id
     * @return New comment with id
     */
    @Override
    public CommentDetailsDto createComment(Long newsId, NewCommentDto newCommentDto) {
        return commentService.createComment(newsId, newCommentDto);
    }

    /**
     * Displays a specific news comment
     *
     * @param newsId    News id
     * @param commentId Comment id
     * @return Comment with details
     */
    @Override
    public CommentDetailsDto getComment(Long newsId, Long commentId) {
        return commentService.getComment(newsId, commentId);
    }

    /**
     * Updates comment fields without changing the ID
     *
     * @param newsId           News id
     * @param commentId        Comment id
     * @param updateCommentDto Updated comment without id
     * @return Comment with details
     */
    @Override
    public CommentDetailsDto updateComment(Long newsId, Long commentId, UpdateCommentDto updateCommentDto) {
        return commentService.updateComment(newsId, commentId, updateCommentDto);
    }

    /**
     * Deletes the comment if it exists
     *
     * @param newsId    News id
     * @param commentId Comment id
     */
    @Override
    public void deleteComment(Long newsId, Long commentId) {
        commentService.deleteComment(newsId, commentId);
    }

    /**
     * Searches for comments containing the search text and belonging to the same news article.
     * If no search text is provided, displays all comments
     *
     * @param newsId   News id
     * @param query    Search text
     * @param pageable Settings of page
     * @return The news article and its comment pages
     */
    @Override
    public NewsDetailsDto findCommentByNews(Long newsId, String query, Pageable pageable) {
        return commentService.findCommentByNews(newsId, query, pageable);
    }

}
