package ru.clevertec.newspaper.core.news.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;

import java.util.List;

public interface NewsService {
    NewsDetailsDto createNews(NewNewsDto newNewsDto);

    NewsDetailsDto findNews(Long newsId);

    NewsDetailsDto updateNews(Long newsId, UpdateNewsDto updateNewsDto);

    void deleteNews(Long newsId);

    List<NewsTitleDto> findNews(String query, Pageable pageable);

    boolean isAuthor(Long newsId, String username);
}
