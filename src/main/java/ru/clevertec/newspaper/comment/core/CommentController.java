package ru.clevertec.newspaper.comment.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<FullNewsDto> getAllCommentByNews(Long newsId,
                                                           Pageable pageable) {
        FullNewsDto fullNewsDto = commentService.getAllCommentOfNews(newsId, pageable);
        return new ResponseEntity<>(fullNewsDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<FullCommentDto>> findComments(Long newsId,
                                                             String q,
                                                             Pageable pageable) {
        List<FullCommentDto> findFullCommentDto = commentService.findComments(newsId, q, pageable);
        return new ResponseEntity<>(findFullCommentDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FullCommentDto> createComment(Long newsId,
                                                        NewCommentDto newCommentDto) {
        FullCommentDto comment = commentService.createComment(newsId, newCommentDto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FullCommentDto> getComment(Long newsId,
                                                     Long commentId) {
        FullCommentDto comment = commentService.findComment(newsId, commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FullCommentDto> editComment(Long newsId,
                                                      Long commentId,
                                                      EditCommentDto editCommentDto) {
        FullCommentDto commentUpdatedDto = commentService.updateComment(newsId, commentId, editCommentDto);
        return new ResponseEntity<>(commentUpdatedDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteComment(Long newsId,
                                                Long commentId) {
        commentService.deleteComment(newsId, commentId);
        return ResponseEntity.noContent().build();
    }

}
