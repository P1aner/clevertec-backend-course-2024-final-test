package ru.clevertec.newspaper.comment.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newspaper.comment.api.CommentRestController;
import ru.clevertec.newspaper.comment.api.dto.EditCommentDto;
import ru.clevertec.newspaper.comment.api.dto.FullCommentDto;
import ru.clevertec.newspaper.comment.api.dto.NewCommentDto;
import ru.clevertec.newspaper.news.api.dto.FullNewsDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CommentController implements CommentRestController {
    private final CommentService commentService;


    @Override
    public FullNewsDto getAllCommentByNews(Long newsId, Pageable pageable) {
        return commentService.getAllCommentOfNews(newsId, pageable);
    }

    @Override
    public List<FullCommentDto> findComments(Long newsId, String q, Pageable pageable) {
        return commentService.findComments(newsId, q, pageable);
    }

    @Override
    public FullCommentDto createComment(Long newsId, NewCommentDto newCommentDto) {
        return commentService.createComment(newsId, newCommentDto);
    }

    @Override
    public FullCommentDto getComment(Long newsId, Long commentId) {
        return commentService.findComment(newsId, commentId);
    }

    @Override
    public FullCommentDto editComment(Long newsId, Long commentId, EditCommentDto editCommentDto) {
        return commentService.updateComment(newsId, commentId, editCommentDto);
    }

    @Override
    public void deleteComment(Long newsId, Long commentId) {
        commentService.deleteComment(newsId, commentId);
    }

}
