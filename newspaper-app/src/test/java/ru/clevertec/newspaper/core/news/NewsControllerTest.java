package ru.clevertec.newspaper.core.news;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.newspaper.api.news.dto.NewsDetailsDto;
import ru.clevertec.newspaper.api.news.dto.NewsTitleDto;

import java.util.List;

@WebMvcTest(NewsController.class)
class NewsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Test
    void createNews() throws Exception {
        NewsDetailsDto newsDetailsDto = NewsDataTest.newsDetailsDtoWithoutComments();

        Mockito.when(newsService.createNews(Mockito.any())).thenReturn(newsDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(NewsDataTest.NEW_NEWS_CONTENT))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().json(NewsDataTest.FULL_NEWS));
    }

    @Test
    void getNews() throws Exception {
        NewsDetailsDto newsDetailsDto = NewsDataTest.newsDetailsDtoWithoutComments();

        Mockito.when(newsService.findNews(Mockito.any())).thenReturn(newsDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/news/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(NewsDataTest.FULL_NEWS));
    }

    @Test
    void updateNews() throws Exception {
        NewsDetailsDto newsDetailsDto = NewsDataTest.newsDetailsDtoWithoutComments();

        Mockito.when(newsService.updateNews(Mockito.any(), Mockito.any())).thenReturn(newsDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/news/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(NewsDataTest.NEW_NEWS_CONTENT))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(NewsDataTest.FULL_NEWS));
    }

    @Test
    void deleteNews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/news/1"))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void findNews() throws Exception {
        NewsTitleDto newsTitleDto = NewsDataTest.newsTitleDto();
        Mockito.when(newsService.findNews(Mockito.any(), Mockito.any())).thenReturn(List.of(newsTitleDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/news")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(NewsDataTest.TITLE_NEWS_LIST));
    }
}