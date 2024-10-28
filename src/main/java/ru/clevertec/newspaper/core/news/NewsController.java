package ru.clevertec.newspaper.core.news;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newspaper.api.news.NewsApi;
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsController implements NewsApi {
    private final NewsService newsService;

    @Override
    public NewsDetailsDto createNews(NewNewsDto fullNewsDto) {
        return newsService.createNews(fullNewsDto);
    }

    @Override
    public NewsDetailsDto getNews(Long newsId) {
        return newsService.findNews(newsId);
    }

    @Override
    public NewsDetailsDto updateNews(Long newsId, UpdateNewsDto updateNewsDto) {
        return newsService.updateNews(newsId, updateNewsDto);
    }

    @Override
    public void deleteNews(Long newsId) {
        newsService.deleteNews(newsId);
    }

    @Override
    public List<NewsTitleDto> findNews(String query, Pageable pageable) {
        return newsService.findNews(query, pageable);
    }

}
