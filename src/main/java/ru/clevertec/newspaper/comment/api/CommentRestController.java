package ru.clevertec.newspaper.comment.api;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.OK)
    FullNewsDto getAllCommentByNews(@PathVariable Long newsId,
                                    Pageable pageable);

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    List<FullCommentDto> findComments(@PathVariable Long newsId,
                                      @RequestParam String q,
                                      Pageable pageable);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    FullCommentDto createComment(@PathVariable Long newsId,
                                 @RequestBody NewCommentDto newCommentDto);

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    FullCommentDto getComment(@PathVariable Long newsId,
                              @PathVariable Long commentId);

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    FullCommentDto editComment(@PathVariable Long newsId,
                               @PathVariable Long commentId,
                               @RequestBody EditCommentDto editCommentDto);

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteComment(@PathVariable Long newsId,
                       @PathVariable Long commentId);
}
