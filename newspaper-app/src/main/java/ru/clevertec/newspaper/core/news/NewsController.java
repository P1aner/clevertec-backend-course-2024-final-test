package ru.clevertec.newspaper.core.news;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newspaper.api.news.NewsApi;
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;

import java.util.List;

/**
 * Provides tools for working with the news
 */
@RestController
@RequiredArgsConstructor
public class NewsController implements NewsApi {
    private final NewsService newsService;

    /**
     * Adds the news
     *
     * @param newNewsDto News without id
     * @return News with details
     */
    @Override
    @PreAuthorize("hasRole('ADMIN') || (hasRole('JOURNALIST') && (#newNewsDto.username == authentication.name))")
    public NewsDetailsDto createNews(NewNewsDto newNewsDto) {
        return newsService.createNews(newNewsDto);
    }

    /**
     * Displays the news with details
     *
     * @param newsId News id
     * @return News with details
     */
    @Override
    public NewsDetailsDto getNews(Long newsId) {
        return newsService.findNews(newsId);
    }

    /**
     * Updates news fields without changing the ID
     *
     * @param newsId        News id
     * @param updateNewsDto News without id
     * @return News with details
     */
    @Override
    @AdminAndNewsOwnerCanEdit
    public NewsDetailsDto updateNews(Long newsId, UpdateNewsDto updateNewsDto) {
        return newsService.updateNews(newsId, updateNewsDto);
    }

    /**
     * Deletes the news if it exists
     *
     * @param newsId News id
     */
    @Override
    @AdminAndNewsOwnerCanEdit
    public void deleteNews(Long newsId) {
        newsService.deleteNews(newsId);
    }

    /**
     * Searches for news containing the search text.
     * If no search text is provided, displays all news.
     *
     * @param query    Search text
     * @param pageable Settings of page
     * @return News list
     */
    @Override
    public List<NewsTitleDto> findNews(String query, Pageable pageable) {
        return newsService.findNews(query, pageable);
    }

}
