package ru.clevertec.newspaper.news.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.newspaper.common.exception.ResourceNotFoundException;
import ru.clevertec.newspaper.news.api.dto.EdinNewsDto;
import ru.clevertec.newspaper.news.api.dto.FullNewsDto;
import ru.clevertec.newspaper.news.api.dto.NewNewsDto;
import ru.clevertec.newspaper.news.api.dto.ShortNewsDto;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;


    public FullNewsDto createNews(NewNewsDto newNewsDto) {
        News news = newsMapper.toNews(newNewsDto);
        News save = newsRepository.save(news);
        return newsMapper.toNewsDto(save);
    }

    public FullNewsDto updateNews(Long id, EdinNewsDto edinNewsDto) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("News id: %s not found.", id)));
        newsMapper.updateNewsFromDto(edinNewsDto, news);
        News save = newsRepository.save(news);
        return newsMapper.toNewsDto(save);
    }

    public FullNewsDto findNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("News id: %s not found.", id)));
        return newsMapper.toNewsDto(news);
    }

    public List<ShortNewsDto> findNews(String q, Pageable pageable) {
        Page<News> byTitleContainsIgnoreCaseAndTextContainsIgnoreCase = newsRepository.findByTitleContainsIgnoreCaseAndTextContainsIgnoreCase(q, q, pageable);
        return newsMapper.toShortNewsListDto(byTitleContainsIgnoreCaseAndTextContainsIgnoreCase.getContent());
    }

    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("News id: %s not exist.", id));
        }
        newsRepository.deleteById(id);
    }

    public List<ShortNewsDto> getAllNews(Pageable pageable) {
        Page<News> newsPage = newsRepository.findAll(pageable);
        return newsMapper.toShortNewsListDto(newsPage.getContent());
    }

}
