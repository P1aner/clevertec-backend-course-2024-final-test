package ru.clevertec.newspaper.core.comment.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;

public interface CommentService {
    CommentDetailsDto createComment(Long newsId, NewCommentDto newCommentDto);

    CommentDetailsDto getComment(Long newsId, Long commentId);

    CommentDetailsDto updateComment(Long newsId, Long commentId, UpdateCommentDto updateCommentDto);

    void deleteComment(Long newsId, Long commentId);

    NewsDetailsDto findCommentByNews(Long newsId, String query, Pageable pageable);
}
