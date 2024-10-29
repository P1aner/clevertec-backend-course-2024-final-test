package ru.clevertec.newspaper.api.news;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;

import java.util.List;

@RestController
@RequestMapping("api/news")
@Validated
@Tag(name = "news", description = "News operation")
public interface NewsApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create news", description = "This POST method takes a json as input and creates an object in the database")
    @ApiResponse(responseCode = "201", description = "Successful operation. Object created.")
    NewsDetailsDto createNews(@RequestBody NewNewsDto fullNewsDto);

    @GetMapping("{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get news", description = "This GET method show a news")
    @ApiResponse(responseCode = "200", description = "Successful operation.")
    NewsDetailsDto getNews(@PathVariable
                           @Parameter(description = "News Id",
                               example = "1",
                               required = true) Long newsId);

    @PutMapping("{newsId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update news", description = "This PUT method update a news")
    @ApiResponse(responseCode = "200", description = "Successful operation.")
    NewsDetailsDto updateNews(@PathVariable
                              @Parameter(description = "News Id",
                                  example = "1",
                                  required = true) Long newsId,
                              @RequestBody UpdateNewsDto updateNewsDto);

    @DeleteMapping("{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete news", description = "This DELETE method deleted a news")
    @ApiResponse(responseCode = "204", description = "Successful operation.")
    void deleteNews(@PathVariable
                    @Parameter(description = "News Id",
                        example = "1",
                        required = true) Long newsId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Find news", description = "This GET method find all news or show news containing query text")
    @ApiResponse(responseCode = "200", description = "Successful operation.")
    List<NewsTitleDto> findNews(@RequestParam(required = false)
                                @Parameter(description = "Search condition",
                                    example = "example") String query,
                                @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable);
}
