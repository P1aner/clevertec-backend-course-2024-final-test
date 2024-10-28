package ru.clevertec.newspaper.api.news;

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
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;

import java.util.List;

@RestController
@RequestMapping("api/news")
public interface NewsApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    NewsDetailsDto createNews(@RequestBody NewNewsDto fullNewsDto);

    @GetMapping("{newsId}")
    @ResponseStatus(HttpStatus.OK)
    NewsDetailsDto getNews(@PathVariable Long newsId);

    @PutMapping("{newsId}")
    @ResponseStatus(HttpStatus.OK)
    NewsDetailsDto updateNews(@PathVariable Long newsId,
                              @RequestBody UpdateNewsDto updateNewsDto);

    @DeleteMapping("{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteNews(@PathVariable Long newsId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<NewsTitleDto> findNews(@RequestParam(required = false) String query,
                                @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable);
}
