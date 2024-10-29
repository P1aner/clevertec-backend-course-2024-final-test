package ru.clevertec.newspaper.core.news;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;
import ru.clevertec.newspaper.exception.ProblemUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;


    public NewsDetailsDto createNews(NewNewsDto newNewsDto) {
        News news = newsMapper.toNews(newNewsDto);
        News save = newsRepository.save(news);
        return newsMapper.toNewsDto(save);
    }

    public NewsDetailsDto findNews(Long id) {
        News news = newsRepository.findById(id)
            .orElseThrow(() -> ProblemUtils.newsNotFound(id));
        return newsMapper.toNewsDto(news);
    }

    public NewsDetailsDto updateNews(Long id, UpdateNewsDto updateNewsDto) {
        News news = newsRepository.findById(id)
            .orElseThrow(() -> ProblemUtils.newsNotFound(id));
        newsMapper.updateNewsFromDto(updateNewsDto, news);
        News save = newsRepository.save(news);
        return newsMapper.toNewsDto(save);
    }

    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw ProblemUtils.newsNotFound(id);
        }
        newsRepository.deleteById(id);
    }

    public List<NewsTitleDto> findNews(String query, Pageable pageable) {
        Page<News> newsPage;
        if (StringUtils.isEmpty(query)) {
            log.debug("Query is empty");
            newsPage = newsRepository.findAll(pageable);
        } else {
            log.debug("Query is not empty");
            newsPage = newsRepository.findByTitleContainsIgnoreCaseAndTextContainsIgnoreCase(query, query, pageable);
        }
        return newsMapper.toShortNewsListDto(newsPage.getContent());
    }

}
