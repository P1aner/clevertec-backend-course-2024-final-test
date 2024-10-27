package ru.clevertec.newspaper.news.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public List<ShortNewsDto> getAllNews(Pageable pageable) {
        return newsService.getAllNews(pageable);
    }

    @Override
    public List<ShortNewsDto> findNews(String q, Pageable pageable) {
        return newsService.findNews(q, pageable);
    }

    @Override
    public FullNewsDto createNews(NewNewsDto fullNewsDto) {
        return newsService.createNews(fullNewsDto);
    }

    @Override
    public FullNewsDto getNews(Long newsId) {
        return newsService.findNews(newsId);
    }

    @Override
    public FullNewsDto editNews(Long newsId, EdinNewsDto edinNewsDto) {
        return newsService.updateNews(newsId, edinNewsDto);
    }

    @Override
    public void deleteNews(Long newsId) {
        newsService.deleteNews(newsId);
    }

}
