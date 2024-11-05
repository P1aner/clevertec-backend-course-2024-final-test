package ru.clevertec.newspaper.core.news;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.cache.Cache;
import ru.clevertec.exception.exception.ProblemUtil;
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;

import java.util.List;

/**
 * Provides tools for working with the news
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final Cache<String, Object> cache;

    /**
     * Adds the news
     *
     * @param newNewsDto News without id
     * @return News with details
     */
    public NewsDetailsDto createNews(NewNewsDto newNewsDto) {
        News news = newsMapper.toNews(newNewsDto);
        News save = newsRepository.save(news);

        String key = cache.generateKey(News.class, save.getId());
        cache.put(key, save);

        return newsMapper.toNewsDto(save);
    }

    /**
     * Displays the news with details
     * If the news is not found, it throws a ResourceNotFoundException.
     *
     * @param newsId News id
     * @return News with details
     */
    public NewsDetailsDto findNews(Long newsId) {
        String key = cache.generateKey(News.class, newsId);
        return newsMapper.toNewsDto(cache.get(key)
            .map(m -> (News) m)
            .orElseGet(() -> {
                News news = newsRepository.findById(newsId)
                    .orElseThrow(() -> ProblemUtil.newsNotFound(newsId));
                cache.put(key, news);
                return news;
            }));
    }

    /**
     * Updates news fields without changing the ID
     * If the news is not found, it throws a ResourceNotFoundException.
     *
     * @param newsId        News id
     * @param updateNewsDto News without id
     * @return News with details
     */
    public NewsDetailsDto updateNews(Long newsId, UpdateNewsDto updateNewsDto) {
        News news = newsRepository.findById(newsId)
            .orElseThrow(() -> ProblemUtil.newsNotFound(newsId));
        newsMapper.updateNewsFromDto(updateNewsDto, news);
        News save = newsRepository.save(news);

        String key = cache.generateKey(News.class, newsId);
        cache.put(key, save);

        return newsMapper.toNewsDto(save);
    }

    /**
     * Deletes the news if it exists
     * If the news is not found, it throws a ResourceNotFoundException.
     *
     * @param newsId News id
     */
    public void deleteNews(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw ProblemUtil.newsNotFound(newsId);
        }
        newsRepository.deleteById(newsId);

        String key = cache.generateKey(News.class, newsId);
        cache.delete(key);
    }

    /**
     * Searches for news containing the search text.
     * If no search text is provided, displays all news.
     *
     * @param query    Search text
     * @param pageable Settings of page
     * @return News list
     */
    public List<NewsTitleDto> findNews(String query, Pageable pageable) {
        Page<News> newsPage;
        if (StringUtils.isEmpty(query)) {
            log.debug("Query is not empty");
            newsPage = newsRepository.findAll(pageable);
        } else {
            log.debug("Query is empty");
            newsPage = newsRepository.findByTitleContainsIgnoreCaseAndTextContainsIgnoreCase(query, query, pageable);
        }
        return newsMapper.toShortNewsListDto(newsPage.getContent());
    }

}