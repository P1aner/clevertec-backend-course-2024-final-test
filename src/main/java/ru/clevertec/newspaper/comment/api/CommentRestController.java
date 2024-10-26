package ru.clevertec.newspaper.comment.api;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newspaper.comment.api.dto.EditCommentDto;
import ru.clevertec.newspaper.comment.api.dto.FullCommentDto;
import ru.clevertec.newspaper.comment.api.dto.NewCommentDto;
import ru.clevertec.newspaper.news.api.dto.FullNewsDto;

import java.util.List;

@RestController
@RequestMapping("/news/{newsId}/comments")
public interface CommentRestController {
    @GetMapping
    ResponseEntity<FullNewsDto> getAllCommentByNews(@PathVariable Long newsId,
                                                    Pageable pageable);

    @GetMapping("/search")
    ResponseEntity<List<FullCommentDto>> findComments(@PathVariable Long newsId,
                                                      @RequestParam String q,
                                                      Pageable pageable);

    @PostMapping
    ResponseEntity<FullCommentDto> createComment(@PathVariable Long newsId,
                                                 @RequestBody NewCommentDto newCommentDto);

    @GetMapping("/{commentId}")
    ResponseEntity<FullCommentDto> getComment(@PathVariable Long newsId,
                                              @PathVariable Long commentId);

    @PutMapping("/{commentId}")
    ResponseEntity<FullCommentDto> editComment(@PathVariable Long newsId,
                                               @PathVariable Long commentId,
                                               @RequestBody EditCommentDto editCommentDto);

    @DeleteMapping("/{commentId}")
    ResponseEntity<Object> deleteComment(@PathVariable Long newsId,
                                         @PathVariable Long commentId);
}
