package ru.clevertec.newspaper.core.news;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.cache.Cache;
import ru.clevertec.exception.exception.ResourceNotFoundException;
import ru.clevertec.newspaper.api.news.dto.NewNewsDto;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;
import ru.clevertec.newspaper.api.news.dto.UpdateNewsDto;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsMapper newsMapper;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private Cache<String, Object> cache;

    @InjectMocks
    private NewsService newsService;

    @Test
    @DisplayName("Save news to repository")
    void createNews() {
        NewNewsDto newNewsDto = NewsData.newNewsDto();
        News fullNewsWithoutComments = NewsData.fullNewsWithoutComments();
        NewsDetailsDto newsDetailsDto = NewsData.newsDetailsDto();

        Mockito.when(newsMapper.toNews(newNewsDto)).thenReturn(fullNewsWithoutComments);
        Mockito.when(newsRepository.save(fullNewsWithoutComments)).thenReturn(fullNewsWithoutComments);
        Mockito.when(newsMapper.toNewsDto(fullNewsWithoutComments)).thenReturn(newsDetailsDto);

        NewsDetailsDto news = newsService.createNews(newNewsDto);

        Mockito.verify(newsMapper, Mockito.times(1)).toNews(newNewsDto);
        Mockito.verify(newsRepository, Mockito.times(1)).save(fullNewsWithoutComments);
        Mockito.verify(newsMapper, Mockito.times(1)).toNewsDto(fullNewsWithoutComments);

        Assertions.assertEquals(news, newsDetailsDto);

    }

    @Test
    @DisplayName("Find news from repository")
    void findNews() {
        News fullNewsWithoutComments = NewsData.fullNewsWithoutComments();
        NewsDetailsDto newsDetailsDto = NewsData.newsDetailsDto();

        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.of(fullNewsWithoutComments));
        Mockito.when(newsMapper.toNewsDto(fullNewsWithoutComments)).thenReturn(newsDetailsDto);

        NewsDetailsDto news = newsService.findNews(1L);

        Mockito.verify(newsRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(newsMapper, Mockito.times(1)).toNewsDto(fullNewsWithoutComments);

        Assertions.assertEquals(news, newsDetailsDto);
    }

    @Test
    @DisplayName("Find non-existent news from repository")
    void failFindNews() {
        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> newsService.findNews(1L));
    }

    @Test
    @DisplayName("Update news from repository")
    void updateNews() {
        News fullNewsWithoutComments = NewsData.fullNewsWithoutComments();
        NewsDetailsDto newsDetailsDto = NewsData.newsDetailsDto();
        UpdateNewsDto updateNewsDto = NewsData.updateNewsDto();

        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.of(fullNewsWithoutComments));
        Mockito.when(newsRepository.save(fullNewsWithoutComments)).thenReturn(fullNewsWithoutComments);
        Mockito.when(newsMapper.toNewsDto(fullNewsWithoutComments)).thenReturn(newsDetailsDto);

        NewsDetailsDto updateNews = newsService.updateNews(1L, updateNewsDto);

        Mockito.verify(newsRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(newsMapper, Mockito.times(1)).updateNewsFromDto(updateNewsDto, fullNewsWithoutComments);
        Mockito.verify(newsMapper, Mockito.times(1)).toNewsDto(fullNewsWithoutComments);

        Assertions.assertEquals(updateNews, newsDetailsDto);
    }

    @Test
    @DisplayName("Update non-existent news from repository")
    void failUpdateNews() {
        UpdateNewsDto updateNewsDto = NewsData.updateNewsDto();

        Mockito.when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> newsService.updateNews(1L, updateNewsDto));
    }

    @Test
    @DisplayName("Delete news from repository")
    void delete() {
        Mockito.when(newsRepository.existsById(1L)).thenReturn(true);

        newsService.deleteNews(1L);

        Mockito.verify(newsRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Delete non-existent news from repository")
    void failDelete() {
        Mockito.when(newsRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> newsService.deleteNews(1L));
    }

    @Test
    @DisplayName("Find news list without query")
    void testFindNewsWithoutQuery() {
        Pageable pageable = Pageable.unpaged();
        String query = "";
        News fullNewsWithoutComments = NewsData.fullNewsWithoutComments();
        NewsTitleDto newsTitleDto = NewsData.newsTitleDto();
        List<News> newsWithoutComments = List.of(fullNewsWithoutComments);
        Page<News> newsPage = new PageImpl<>(newsWithoutComments);

        Mockito.when(newsRepository.findAll(pageable)).thenReturn(newsPage);
        Mockito.when(newsMapper.toShortNewsListDto(newsWithoutComments)).thenReturn(List.of(newsTitleDto));

        List<NewsTitleDto> news = newsService.findNews(query, pageable);

        Mockito.verify(newsRepository, Mockito.times(1)).findAll(pageable);
        Mockito.verify(newsRepository, Mockito.times(0)).findByTitleContainsIgnoreCaseAndTextContainsIgnoreCase(query, query, pageable);

        Assertions.assertEquals(news.getFirst(), newsTitleDto);
    }

    @Test
    @DisplayName("Find news list with query")
    void testFindNewsWithQuery() {
        Pageable pageable = Pageable.unpaged();
        String query = "Query";
        News fullNewsWithoutComments = NewsData.fullNewsWithoutComments();
        NewsTitleDto newsTitleDto = NewsData.newsTitleDto();
        List<News> newsWithoutComments = List.of(fullNewsWithoutComments);
        Page<News> newsPage = new PageImpl<>(newsWithoutComments);

        Mockito.when(newsRepository.findByTitleContainsIgnoreCaseAndTextContainsIgnoreCase(query, query, pageable)).thenReturn(newsPage);
        Mockito.when(newsMapper.toShortNewsListDto(newsWithoutComments)).thenReturn(List.of(newsTitleDto));

        List<NewsTitleDto> news = newsService.findNews(query, pageable);

        Mockito.verify(newsRepository, Mockito.times(0)).findAll(pageable);
        Mockito.verify(newsRepository, Mockito.times(1)).findByTitleContainsIgnoreCaseAndTextContainsIgnoreCase(query, query, pageable);

        Assertions.assertEquals(news.getFirst(), newsTitleDto);
    }
}