package ru.clevertec.newspaper.api.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "comment", description = "Comment operation")
public interface ApiComment {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create comment", description = "This POST method takes a json as input and creates an object in the database")
    @ApiResponse(responseCode = "201", description = "Successful operation. Object created.")
    CommentDetailsDto createComment(@PathVariable
                                    @Parameter(description = "News Id",
                                        example = "1",
                                        required = true) Long newsId,
                                    @RequestBody NewCommentDto newCommentDto);

    @GetMapping("{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get comment", description = "This GET method show a news comment")
    @ApiResponse(responseCode = "200", description = "Successful operation.")
    CommentDetailsDto getComment(@PathVariable
                                 @Parameter(description = "News Id",
                                     example = "1",
                                     required = true) Long newsId,
                                 @PathVariable
                                 @Parameter(description = "Comment Id",
                                     example = "1",
                                     required = true) Long commentId);

    @PutMapping("{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update comment", description = "This PUT method update a news comment")
    @ApiResponse(responseCode = "200", description = "Successful operation.")
    CommentDetailsDto updateComment(@PathVariable
                                    @Parameter(description = "News Id",
                                        example = "1",
                                        required = true) Long newsId,
                                    @PathVariable
                                    @Parameter(description = "Comment Id",
                                        example = "1",
                                        required = true) Long commentId,
                                    @RequestBody UpdateCommentDto updateCommentDto);

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete comment", description = "This DELETE method deleted a news comment")
    @ApiResponse(responseCode = "204", description = "Successful operation.")
    void deleteComment(@PathVariable
                       @Parameter(description = "Comment Id",
                           example = "1",
                           required = true) Long newsId,
                       @PathVariable Long commentId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find news comment", description = "This GET method find all news comments or show comments containing query text")
    @ApiResponse(responseCode = "200", description = "Successful operation.")
    NewsDetailsDto findCommentByNews(@PathVariable
                                     @Parameter(description = "News Id",
                                         example = "1",
                                         required = true) Long newsId,
                                     @RequestParam(required = false)
                                     @Parameter(description = "Search condition",
                                         example = "example") String query,
                                     @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable);
}
