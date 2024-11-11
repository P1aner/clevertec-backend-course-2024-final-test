package ru.clevertec.newspaper.core.news.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDtoList;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;

public interface NewsService {
    NewsDetailsDto createNews(NewNewsDto newNewsDto);

    NewsDetailsDto findNews(Long newsId);

    NewsDetailsDto updateNews(Long newsId, UpdateNewsDto updateNewsDto);

    void deleteNews(Long newsId);

    NewsTitleDtoList findNews(String query, Pageable pageable);

    boolean isAuthor(Long newsId, String username);
}
