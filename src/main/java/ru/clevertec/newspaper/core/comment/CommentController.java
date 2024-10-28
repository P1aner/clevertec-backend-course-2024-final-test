package ru.clevertec.newspaper.core.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newspaper.api.comment.ApiComment;
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;


@RestController
@RequiredArgsConstructor
public class CommentController implements ApiComment {
    private final CommentService commentService;


    @Override
    public CommentDetailsDto createComment(Long newsId, NewCommentDto newCommentDto) {
        return commentService.createComment(newsId, newCommentDto);
    }

    @Override
    public CommentDetailsDto getComment(Long newsId, Long commentId) {
        return commentService.getComment(newsId, commentId);
    }

    @Override
    public CommentDetailsDto updateComment(Long newsId, Long commentId, UpdateCommentDto updateCommentDto) {
        return commentService.updateComment(newsId, commentId, updateCommentDto);
    }

    @Override
    public void deleteComment(Long newsId, Long commentId) {
        commentService.deleteComment(newsId, commentId);
    }


    @Override
    public NewsDetailsDto findCommentByNews(Long newsId, String query, Pageable pageable) {
        return commentService.findCommentByNews(newsId, query, pageable);
    }

}
