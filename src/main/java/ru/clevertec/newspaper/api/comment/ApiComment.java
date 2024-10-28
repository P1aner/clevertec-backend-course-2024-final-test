package ru.clevertec.newspaper.api.comment;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import ru.clevertec.newspaper.api.comment.dto.CommentDetailsDto;
import ru.clevertec.newspaper.api.comment.dto.NewCommentDto;
import ru.clevertec.newspaper.api.comment.dto.UpdateCommentDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;

@RestController
@RequestMapping("api/news/{newsId}/comments")
public interface ApiComment {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CommentDetailsDto createComment(@PathVariable Long newsId,
                                    @RequestBody NewCommentDto newCommentDto);

    @GetMapping("{commentId}")
    @ResponseStatus(HttpStatus.OK)
    CommentDetailsDto getComment(@PathVariable Long newsId,
                                 @PathVariable Long commentId);

    @PutMapping("{commentId}")
    @ResponseStatus(HttpStatus.OK)
    CommentDetailsDto updateComment(@PathVariable Long newsId,
                                    @PathVariable Long commentId,
                                    @RequestBody UpdateCommentDto updateCommentDto);

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteComment(@PathVariable Long newsId,
                       @PathVariable Long commentId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    NewsDetailsDto findCommentByNews(@PathVariable Long newsId,
                                     @RequestParam(required = false) String query,
                                     @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable);
}
