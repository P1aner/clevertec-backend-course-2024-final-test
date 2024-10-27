package ru.clevertec.newspaper.news.api;

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
import ru.clevertec.newspaper.news.api.dto.EdinNewsDto;
import ru.clevertec.newspaper.news.api.dto.FullNewsDto;
import ru.clevertec.newspaper.news.api.dto.NewNewsDto;
import ru.clevertec.newspaper.news.api.dto.ShortNewsDto;

import java.util.List;

@RestController
@RequestMapping("/news")
public interface NewsRestController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ShortNewsDto> getAllNews(Pageable pageable);

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    List<ShortNewsDto> findNews(@RequestParam String q,
                                Pageable pageable);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    FullNewsDto createNews(@RequestBody NewNewsDto fullNewsDto);

    @GetMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    FullNewsDto getNews(@PathVariable Long newsId);

    @PutMapping("/{newsId}")
    @ResponseStatus(HttpStatus.OK)
    FullNewsDto editNews(@PathVariable Long newsId,
                         @RequestBody EdinNewsDto edinNewsDto);

    @DeleteMapping("/{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteNews(@PathVariable Long newsId);
}
