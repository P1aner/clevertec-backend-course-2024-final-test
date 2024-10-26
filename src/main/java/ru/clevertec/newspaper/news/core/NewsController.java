package ru.clevertec.newspaper.news.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newspaper.news.api.NewsRestController;
import ru.clevertec.newspaper.news.api.dto.EdinNewsDto;
import ru.clevertec.newspaper.news.api.dto.FullNewsDto;
import ru.clevertec.newspaper.news.api.dto.NewNewsDto;
import ru.clevertec.newspaper.news.api.dto.ShortNewsDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsController implements NewsRestController {
    private final NewsService newsService;

    @Override
    public ResponseEntity<List<ShortNewsDto>> getAllNews(Pageable pageable) {
        List<ShortNewsDto> shortNewsDto = newsService.getAllNews(pageable);
        return new ResponseEntity<>(shortNewsDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ShortNewsDto>> findNews(String q,
                                                       Pageable pageable) {
        List<ShortNewsDto> findShortNewsDto = newsService.findNews(q, pageable);
        return new ResponseEntity<>(findShortNewsDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FullNewsDto> createNews(NewNewsDto fullNewsDto) {
        FullNewsDto newsDto = newsService.createNews(fullNewsDto);
        return new ResponseEntity<>(newsDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<FullNewsDto> getNews(Long newsId) {
        FullNewsDto news = newsService.findNews(newsId);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FullNewsDto> editNews(Long newsId,
                                                EdinNewsDto edinNewsDto) {
        FullNewsDto newsUpdatedDto = newsService.updateNews(newsId, edinNewsDto);
        return new ResponseEntity<>(newsUpdatedDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteNews(Long newsId) {
        newsService.deleteNews(newsId);
        return ResponseEntity.noContent().build();
    }

}
