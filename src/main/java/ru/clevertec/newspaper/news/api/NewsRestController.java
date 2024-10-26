package ru.clevertec.newspaper.news.api;

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
import ru.clevertec.newspaper.news.api.dto.EdinNewsDto;
import ru.clevertec.newspaper.news.api.dto.FullNewsDto;
import ru.clevertec.newspaper.news.api.dto.NewNewsDto;
import ru.clevertec.newspaper.news.api.dto.ShortNewsDto;

import java.util.List;

@RestController
@RequestMapping("/news")
public interface NewsRestController {
    @GetMapping
    ResponseEntity<List<ShortNewsDto>> getAllNews(Pageable pageable);

    @GetMapping("/search")
    ResponseEntity<List<ShortNewsDto>> findNews(@RequestParam String q,
                                                Pageable pageable);

    @PostMapping
    ResponseEntity<FullNewsDto> createNews(@RequestBody NewNewsDto fullNewsDto);

    @GetMapping("/{newsId}")
    ResponseEntity<FullNewsDto> getNews(@PathVariable Long newsId);

    @PutMapping("/{newsId}")
    ResponseEntity<FullNewsDto> editNews(@PathVariable Long newsId,
                                         @RequestBody EdinNewsDto edinNewsDto);

    @DeleteMapping("/{newsId}")
    ResponseEntity<Object> deleteNews(@PathVariable Long newsId);
}
